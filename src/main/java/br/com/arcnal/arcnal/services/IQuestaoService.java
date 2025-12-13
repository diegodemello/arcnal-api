package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.QuestaoReqDTO;
import br.com.arcnal.arcnal.dtos.QuestaoRespDTO;

import java.util.List;

public interface IQuestaoService {
    QuestaoRespDTO adicionarQuestao(QuestaoReqDTO dto);
    List<QuestaoRespDTO> listarQuestoes();
}
