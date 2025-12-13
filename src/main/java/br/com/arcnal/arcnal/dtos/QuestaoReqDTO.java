package br.com.arcnal.arcnal.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestaoReqDTO(
        @NotNull
        Integer idBanca,
        @NotNull
        Integer idMateria,
        @NotNull
        Integer idAssunto,
        @NotNull
        Integer ano,
        @NotBlank(message = "O enunciado não pode ser vazio.")
        String enunciado,
        @NotBlank(message = "A alternativa A é obrigatória.")
        String alternativaA,
        @NotBlank(message = "A alternativa B é obrigatória.")
        String alternativaB,
        String alternativaC,
        String alternativaD,
        String alternativaE,
        @NotNull(message = "Você precisa informar a alternativa correta.")
        Character alternativaCorreta,
        @NotBlank(message = "Você precisa escrever uma resolução.")
        String textoCorrecao,
        @NotBlank(message = "Você precisa informar a URL do vídeo de resolução.")
        String videoCorrecao
) {
}