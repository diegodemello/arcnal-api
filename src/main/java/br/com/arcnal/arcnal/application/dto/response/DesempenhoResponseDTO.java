package br.com.arcnal.arcnal.application.dto.response;

public record DesempenhoResponseDTO(
        String nome,
        Long acertos,
        Long erros
) {
}
