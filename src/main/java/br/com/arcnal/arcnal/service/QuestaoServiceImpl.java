package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.*;
import br.com.arcnal.arcnal.dto.QuestaoRequestDTO;
import br.com.arcnal.arcnal.dto.QuestaoResponseDTO;
import br.com.arcnal.arcnal.domain.Assunto;
import br.com.arcnal.arcnal.domain.Banca;
import br.com.arcnal.arcnal.domain.Materia;
import br.com.arcnal.arcnal.domain.Questao;
import br.com.arcnal.arcnal.exception.*;
import br.com.arcnal.arcnal.mapper.QuestaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestaoServiceImpl implements IQuestaoService{

    private final QuestaoDAO questaoDAO;
    private final BancaDAO bancaDAO;
    private final MateriaDAO materiaDAO;
    private final AssuntoDAO assuntoDAO;
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
        questaoDAO.save(questao);

        QuestaoResponseDTO questaoResponseDTO = questaoMapper.toResponse(questao);
        return questaoResponseDTO;
    }

    @Override
    public List<QuestaoResponseDTO> listarQuestoes() {
        return questaoMapper.toResponses(questaoDAO.findAll());
    }

    @Override
    public List<QuestaoResponseDTO> listarQuestoesPorFiltro(Integer idBanca, Integer ano, Integer idMateria, Integer idAssunto) {
        Banca banca = idBanca != null ? buscarBancaPorId(idBanca) : null;
        Materia materia = idMateria != null ? buscarMateriaPorId(idMateria) : null;
        Assunto assunto = idAssunto != null ? buscarAssuntoPorId(idAssunto) : null;

        List<Questao> questoes = questaoDAO.findAll(QuestaoSpec.porFiltros(idBanca, ano, idMateria, idAssunto));

        return questaoMapper.toResponses(questoes);
    }

    private void validarEnunciadoRepetido(String enunciado){
        if(questaoDAO.existsByEnunciado(enunciado)){
            throw new EnunciadoExistenteException("Enunciado já existente no sistema.");
        }
    }

    private void validarAno(Integer ano){
        if(ano > ANO_ATUAL){
            throw new AnoInvalidoException("Ano da questão não pode ser maior que o ano atual.");
        }
    }

    private void validarSeExistemQuestoesComFiltrosEscolhidos(List<Questao> questoes){
        if(questoes.isEmpty()){
            throw new QuestaoNaoEncontradaException("Nenhuma questão encontrada com os filtros informados.");
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
        return  bancaDAO.findById(id)
                .orElseThrow(() -> new BancaExistenteException("ID de banca enviado não existe."));
    }

    private Materia buscarMateriaPorId(Integer id){
        return materiaDAO.findById(id)
                .orElseThrow(() -> new MateriaNaoEncontradaException("ID de matéria enviado não existe."));
    }

    private Assunto buscarAssuntoPorId(Integer id){
        return assuntoDAO.findById(id)
                .orElseThrow(() -> new AssuntoNaoEncontradoException("ID de assunto enviado não existe."));
    }
}
