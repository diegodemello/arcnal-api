package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.BancaDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dao.QuestaoDAO;
import br.com.arcnal.arcnal.dtos.QuestaoReqDTO;
import br.com.arcnal.arcnal.dtos.QuestaoRespDTO;
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

    Integer anoAtual = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    public QuestaoRespDTO adicionarQuestao(QuestaoReqDTO dto) {
        if(questaoDAO.existsByEnunciado(dto.enunciado())){
            throw new RuntimeException("Já existe uma questão com esse enunciado.");
        }
        if(dto.ano() > anoAtual){
            throw new RuntimeException("O ano enviado não pode ser no futuro.");
        }
        Banca banca = bancaDAO.findById(dto.idBanca())
                .orElseThrow(() -> new RuntimeException("ID de banca enviado não existe."));
        Materia materia = materiaDAO.findById(dto.idMateria())
                .orElseThrow(() -> new RuntimeException("ID de matéria enviado não existe."));
        Assunto assunto = assuntoDAO.findById(dto.idAssunto())
                .orElseThrow(() -> new RuntimeException("ID de assunto enviado não existe."));

        char alt = dto.alternativaCorreta();

        if(alt != 'A' && alt != 'B' && alt != 'C' && alt != 'D' && alt != 'E'){
            throw new RuntimeException("A alternativa correta deve ser uma alternativa válida.");
        }

        Questao questao = questaoMapper.requestToEntity(dto);
        questao.setBanca(banca);
        questao.setMateria(materia);
        questao.setAssunto(assunto);
        questaoDAO.save(questao);

        QuestaoRespDTO questaoRespDTO = questaoMapper.entityToDto(questao);
        return questaoRespDTO;
    }

    @Override
    public List<QuestaoRespDTO> listarQuestoes() {
        return questaoMapper.entitiesToDtos(questaoDAO.findAll());
    }
}
