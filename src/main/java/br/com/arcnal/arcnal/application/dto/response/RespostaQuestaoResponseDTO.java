package br.com.arcnal.arcnal.application.dto.response;

public record RespostaQuestaoResponseDTO(
    Integer idQuestao,
    Character alternativaEscolhida,
    boolean acertou,
    Character alternativaCorreta
) {
}
