package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.QuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.ResolucaoQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.RespostaQuestaoResponseDTO;

import java.util.List;

public interface IQuestaoService {
    QuestaoResponseDTO adicionarQuestao(QuestaoRequestDTO dto);
    List<QuestaoResponseDTO> listarQuestoes(Integer pagina, Integer objetos);
    List<QuestaoResponseDTO> listarQuestoesPorFiltro(Integer pagina, Integer objetos,
                                                     Integer idBanca, Integer ano,
                                                     Integer idMateria, Integer idAssunto);
    RespostaQuestaoResponseDTO responderQuestao(Integer idQuestao, Character alternativaEscolhida);
    ResolucaoQuestaoResponseDTO obterResolucaoQuestao(Integer idQuestao);
}