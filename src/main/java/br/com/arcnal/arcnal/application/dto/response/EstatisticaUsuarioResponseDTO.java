package br.com.arcnal.arcnal.application.dto.response;

import java.util.List;

public record EstatisticaUsuarioResponseDTO(
        Long totalDeQuestoesRespondidas,
        Double taxaDeAcerto,
        Long quantidadeDeRevisoes,
        Long materiasEstudadas,
        List<DesempenhoResponseDTO> desempenhoPorMateria,
        List<DesempenhoResponseDTO> desempenhoPorConteudo
) {
}