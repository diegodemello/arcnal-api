package br.com.arcnal.arcnal.dto;

public record RespostaQuestaoResponseDTO(
    Integer idQuestao,
    Character alternativaEscolhida,
    boolean acertou
) {
}
