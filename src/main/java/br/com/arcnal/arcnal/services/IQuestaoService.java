package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.QuestaoRequestDTO;
import br.com.arcnal.arcnal.dtos.QuestaoResponseDTO;

import java.util.List;

public interface IQuestaoService {
    QuestaoResponseDTO adicionarQuestao(QuestaoRequestDTO dto);
    List<QuestaoResponseDTO> listarQuestoes();
}
