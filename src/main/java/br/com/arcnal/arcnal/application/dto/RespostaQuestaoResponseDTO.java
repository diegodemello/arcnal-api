package br.com.arcnal.arcnal.application.dto;

public record RespostaQuestaoResponseDTO(
    Integer idQuestao,
    Character alternativaEscolhida,
    boolean acertou,
    Character alternativaCorreta
) {
}
