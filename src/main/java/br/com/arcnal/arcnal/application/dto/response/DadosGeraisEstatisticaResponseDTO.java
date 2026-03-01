package br.com.arcnal.arcnal.application.dto.response;

public record DadosGeraisEstatisticaResponseDTO(
        Long totalRespondidas,
        Long totalAcertos,
        Long totalErros,
        Long materiasEstudadas
) {
}
