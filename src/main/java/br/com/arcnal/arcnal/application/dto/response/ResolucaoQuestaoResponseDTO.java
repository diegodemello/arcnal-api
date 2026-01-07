package br.com.arcnal.arcnal.application.dto.response;

public record ResolucaoQuestaoResponseDTO(
        Integer id,
        Character alternativaCorreta,
        String textoCorrecao,
        String videoCorrecao
) {
}
