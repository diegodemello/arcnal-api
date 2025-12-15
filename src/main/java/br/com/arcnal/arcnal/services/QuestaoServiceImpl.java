package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dao.QuestaoDAO;
import br.com.arcnal.arcnal.dtos.QuestaoRequestDTO;
import br.com.arcnal.arcnal.dtos.QuestaoResponseDTO;
import br.com.arcnal.arcnal.entities.Assunto;
import br.com.arcnal.arcnal.entities.Banca;
import br.com.arcnal.arcnal.entities.Materia;
import br.com.arcnal.arcnal.entities.Questao;
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

    private void validarEnunciadoRepetido(String enunciado){
        if(questaoDAO.existsByEnunciado(enunciado)){
            throw new RuntimeException("Já existe uma questão com esse enunciado.");
        }
    }

    private void validarAno(Integer ano){
        if(ano > ANO_ATUAL){
            throw new RuntimeException("O ano enviado não pode ser no futuro.");
        }
    }

    private void validarAlternativaCorreta(char alternativa){
        if(alternativa != 'A'
                && alternativa != 'B'
                && alternativa != 'C'
                && alternativa != 'D'
                && alternativa != 'E'){
            throw new RuntimeException("A alternativa correta deve ser A, B, C, D ou E.");
        }
    }

    private Banca buscarBancaPorId(Integer id){
        return  bancaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de banca enviado não existe."));
    }

    private Materia buscarMateriaPorId(Integer id){
        return materiaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de matéria enviado não existe."));
    }

    private Assunto buscarAssuntoPorId(Integer id){
        return assuntoDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de assunto enviado não existe."));
    }
}
