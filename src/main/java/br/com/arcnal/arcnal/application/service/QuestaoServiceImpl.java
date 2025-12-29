package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.domain.exception.*;
import br.com.arcnal.arcnal.domain.repositories.*;
import br.com.arcnal.arcnal.application.dto.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.QuestaoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.entities.Banca;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.entities.Questao;
import br.com.arcnal.arcnal.application.dto.ResolucaoQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.RespostaQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.mapper.QuestaoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestaoServiceImpl implements IQuestaoService{

    private final QuestaoRepository questaoRepository;
    private final BancaRepository bancaRepository;
    private final MateriaRepository materiaRepository;
    private final AssuntoRepository assuntoRepository;
    private final QuestaoMapper questaoMapper;

    Integer ANO_ATUAL = Calendar.getInstance().get(Calendar.YEAR);

    @Override
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
        questaoRepository.save(questao);
        log.info("Questão criada com id = " + questao.getId());

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
        log.info("Questão com id = " + idQuestao + " foi respondida.");
        if (verificarRespostaCorreta(questao, alternativaEscolhida)){
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

    private void validarEnunciadoRepetido(String enunciado){
        if(questaoRepository.existsByEnunciado(enunciado)){
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
}
