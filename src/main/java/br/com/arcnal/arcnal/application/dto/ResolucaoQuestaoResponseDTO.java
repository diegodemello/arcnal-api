package br.com.arcnal.arcnal.application.dto;

public record ResolucaoQuestaoResponseDTO(
        Integer id,
        Character alternativaCorreta,
        String textoCorrecao,
        String videoCorrecao
) {
}
