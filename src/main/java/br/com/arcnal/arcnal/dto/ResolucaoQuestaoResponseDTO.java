package br.com.arcnal.arcnal.dto;

public record ResolucaoQuestaoResponseDTO(
        Integer id,
        Character alternativaCorreta,
        String textoCorrecao,
        String videoCorrecao
) {
}
