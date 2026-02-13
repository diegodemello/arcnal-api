package br.com.arcnal.arcnal.application.dto.request;

public record ImagemRequestDTO(
        Integer idQuestao,
        String nomeArquivo,
        String caminho,
        Long tamanho
) {
}
