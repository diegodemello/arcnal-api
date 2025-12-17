package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.QuestaoRequestDTO;
import br.com.arcnal.arcnal.dto.QuestaoResponseDTO;
import br.com.arcnal.arcnal.dto.RespostaQuestaoResponseDTO;

import java.util.List;

public interface IQuestaoService {
    QuestaoResponseDTO adicionarQuestao(QuestaoRequestDTO dto);
    List<QuestaoResponseDTO> listarQuestoes();
    List<QuestaoResponseDTO> listarQuestoesPorFiltro(Integer idBanca, Integer ano,
                                                     Integer idMateria, Integer idAssunto);
    RespostaQuestaoResponseDTO responderQuestao(Integer idQuestao, Character alternativaEscolhida);
}