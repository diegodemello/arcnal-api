package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.request.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.QuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.ResolucaoQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RespostaQuestaoResponseDTO;

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