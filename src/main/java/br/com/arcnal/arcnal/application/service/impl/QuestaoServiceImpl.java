package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.IQuestaoService;
import br.com.arcnal.arcnal.domain.entities.*;
import br.com.arcnal.arcnal.domain.exception.*;
import br.com.arcnal.arcnal.domain.repositories.*;
import br.com.arcnal.arcnal.application.dto.request.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.QuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.ResolucaoQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RespostaQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.mapper.QuestaoMapper;
import br.com.arcnal.arcnal.domain.valueobjects.ArquivoInfo;
import br.com.arcnal.arcnal.infra.storage.AzureBlobStorageService;
import br.com.arcnal.arcnal.infra.util.AuthFacade;
import io.micrometer.core.instrument.Counter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestaoServiceImpl implements IQuestaoService {

    private final QuestaoRepository questaoRepository;
    private final BancaRepository bancaRepository;
    private final MateriaRepository materiaRepository;
    private final AssuntoRepository assuntoRepository;
    private final QuestaoMapper questaoMapper;
    private final RespostaUsuarioRepository respostaUsuarioRepository;
    private final AuthFacade authFacade;
    private final AzureBlobStorageService azureBlobStorageService;
    private final ImagemRepository imagemRepository;

    @Qualifier("questoesCriadas")
    private final Counter questoesCriadas;

    Integer ANO_ATUAL = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    @Transactional
    public QuestaoResponseDTO adicionarQuestao(QuestaoRequestDTO dto) {
        validarEnunciadoRepetido(dto.enunciado());
        validarAno(dto.ano());
        validarAlternativaCorreta(dto.alternativaCorreta());

        Banca banca = buscarBancaPorId(dto.idBanca());
        Materia materia = buscarMateriaPorId(dto.idMateria());
        Assunto assunto = buscarAssuntoPorId(dto.idAssunto());

        Questao questao = questaoMapper.toEntity(dto);
        questao.setBanca(banca);
        questao.setMateria(materia);
        questao.setAssunto(assunto);

        if(questao.getImagens() == null){
            questao.setImagens(new ArrayList<>());
        }

        if(dto.imagens() != null && !dto.imagens().isEmpty()){
            processarImagens(questao, dto.imagens());
        }

        questaoRepository.save(questao);
        log.info("Questão criada com id = " + questao.getId());
        questoesCriadas.increment();

        QuestaoResponseDTO questaoResponseDTO = questaoMapper.toResponse(questao);
        return questaoResponseDTO;
    }

    @Override
    public List<QuestaoResponseDTO> listarQuestoes(Integer pagina, Integer objetos) {
        return questaoMapper.toResponses(questaoRepository.findAll(PageRequest.of(pagina, objetos)).getContent());
    }

    @Override
    public List<QuestaoResponseDTO> listarQuestoesPorFiltro(Integer pagina, Integer objetos, Integer idBanca, Integer ano, Integer idMateria, Integer idAssunto) {
        Banca banca = idBanca != null ? buscarBancaPorId(idBanca) : null;
        Materia materia = idMateria != null ? buscarMateriaPorId(idMateria) : null;
        Assunto assunto = idAssunto != null ? buscarAssuntoPorId(idAssunto) : null;

        Pageable pageable = PageRequest.of(pagina, objetos);

        List<Questao> questoes = questaoRepository.findAll(QuestaoSpec.porFiltros(idBanca, ano, idMateria, idAssunto), pageable).getContent();

        return questaoMapper.toResponses(questoes);
    }

    @Override
    public RespostaQuestaoResponseDTO responderQuestao(Integer idQuestao, Character alternativaEscolhida) {
        Questao questao = buscarQuestaoPorId(idQuestao);
        validarAlternativaEscolhida(alternativaEscolhida);

        boolean acertou = verificarRespostaCorreta(questao, alternativaEscolhida);

        Usuario usuario = authFacade.getUsuarioAutenticado();
        registrarRespostaUsuario(questao, acertou, usuario);
        log.info("Questão com id = " + idQuestao + " foi respondida.");
        if(acertou){
            return new RespostaQuestaoResponseDTO(idQuestao, alternativaEscolhida, true, questao.getAlternativaCorreta());
        }
        return new RespostaQuestaoResponseDTO(idQuestao, alternativaEscolhida, false, questao.getAlternativaCorreta());
    }

    @Override
    public ResolucaoQuestaoResponseDTO obterResolucaoQuestao(Integer idQuestao) {
        Questao questao = buscarQuestaoPorId(idQuestao);
        log.info("Questão com id = " + idQuestao + " foi visualizada a resolução.");
        return questaoMapper.toResolucaoResponse(questao);
    }

    private void processarImagens(Questao questao, List<MultipartFile> arquivos){
        arquivos.forEach(arquivo -> {
            try{
                String caminho = azureBlobStorageService.salvarArquivo(arquivo);

                Imagem imagem = new Imagem();
                imagem.setArquivoInfo(new ArquivoInfo(
                        arquivo.getOriginalFilename(),
                        caminho,
                        arquivo.getSize()
                ));
                imagem.setQuestao(questao);

                questao.getImagens().add(imagem);

                log.debug("Imagem processada: {}", arquivo.getOriginalFilename());
            } catch (Exception e){
                log.error("Erro ao processar imagem: {}", arquivo.getOriginalFilename(), e);
                throw new ImagemInvalidaException("Erro ao processar imagem: " + arquivo.getOriginalFilename());
            }
        });
    }

    private void validarEnunciadoRepetido(String enunciado){
        if(questaoRepository.existsByMetadadosEnunciado(enunciado)){
            throw new EnunciadoExistenteException("Enunciado já existente no sistema.");
        }
    }

    private void validarAno(Integer ano){
        if(ano > ANO_ATUAL){
            throw new AnoInvalidoException("Ano da questão não pode ser maior que o ano atual.");
        }
    }

    private void validarAlternativaCorreta(char alternativa){
        if(alternativa != 'A'
                && alternativa != 'B'
                && alternativa != 'C'
                && alternativa != 'D'
                && alternativa != 'E'){
            throw new AlternativaInvalidaException("Alternativa correta inválida. Deve ser uma letra entre A e E.");
        }
    }

    private Banca buscarBancaPorId(Integer id){
        return  bancaRepository.findById(id)
                .orElseThrow(() -> new BancaExistenteException("ID de banca enviado não existe."));
    }

    private Materia buscarMateriaPorId(Integer id){
        return materiaRepository.findById(id)
                .orElseThrow(() -> new MateriaNaoEncontradaException("ID de matéria enviado não existe."));
    }

    private Assunto buscarAssuntoPorId(Integer id){
        return assuntoRepository.findById(id)
                .orElseThrow(() -> new AssuntoNaoEncontradoException("ID de assunto enviado não existe."));
    }

    private Questao buscarQuestaoPorId(Integer id){
        return questaoRepository.findById(id)
                .orElseThrow(() -> new QuestaoNaoEncontradaException("ID de questão enviado não existe."));
    }

    private boolean verificarRespostaCorreta(Questao questao, Character alternativaEscolhida){
        if(questao.getAlternativaCorreta().equals(alternativaEscolhida)){
            return true;
        }
        return false;
    }

    private void validarAlternativaEscolhida(Character alternativaEscolhida){
        if(alternativaEscolhida == null){
            throw new AlternativaInvalidaException("Alternativa escolhida não pode ser nula.");
        }
        if(alternativaEscolhida != 'A'
                && alternativaEscolhida != 'B'
                && alternativaEscolhida != 'C'
                && alternativaEscolhida != 'D'
                && alternativaEscolhida != 'E'){
            throw new AlternativaInvalidaException("Alternativa escolhida inválida. Deve ser uma letra entre A e E.");
        }
    }

    private void registrarRespostaUsuario(Questao questao, boolean acertou, Usuario usuario) {
        RespostaUsuario respostaUsuario = new RespostaUsuario();
        respostaUsuario.setUsuario(usuario);
        respostaUsuario.setQuestao(questao);
        respostaUsuario.setMateria(questao.getMateria());
        respostaUsuario.setAssunto(questao.getAssunto());
        respostaUsuario.setDataResposta(LocalDate.now());
        respostaUsuario.setAcertou(acertou);
        respostaUsuarioRepository.save(respostaUsuario);
    }
}
