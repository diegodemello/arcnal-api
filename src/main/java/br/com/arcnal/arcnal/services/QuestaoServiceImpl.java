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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class QuestaoServiceImpl implements IQuestaoService{

    @Autowired
    QuestaoDAO questaoDAO;
    @Autowired
    BancaDAO bancaDAO;
    @Autowired
    MateriaDAO materiaDAO;
    @Autowired
    AssuntoDAO assuntoDAO;

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

        Questao questao = Questao.builder()
                .banca(banca)
                .materia(materia)
                .assunto(assunto)
                .ano(dto.ano())
                .enunciado(dto.enunciado())
                .alternativaA(dto.alternativaA())
                .alternativaB(dto.alternativaB())
                .alternativaC(dto.alternativaC())
                .alternativaD(dto.alternativaD())
                .alternativaE(dto.alternativaE())
                .alternativaCorreta(dto.alternativaCorreta())
                .textoCorrecao(dto.textoCorrecao())
                .videoCorrecao(dto.videoCorrecao())
                .build();
        questaoDAO.save(questao);

        QuestaoRespDTO respDto = QuestaoRespDTO.builder()
                .id(questao.getId())
                .enunciado(questao.getEnunciado())
                .alternativaA(questao.getAlternativaA())
                .alternativaB(questao.getAlternativaB())
                .alternativaC(questao.getAlternativaC())
                .alternativaD(questao.getAlternativaD())
                .alternativaE(questao.getAlternativaE())
                .build();
        return respDto;
    }
}
