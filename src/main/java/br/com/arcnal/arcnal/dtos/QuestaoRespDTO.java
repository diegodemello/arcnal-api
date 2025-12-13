package br.com.arcnal.arcnal.dtos;

import lombok.Builder;

@Builder
public record QuestaoRespDTO(
        Integer id,
        String enunciado,
        String alternativaA,
        String alternativaB,
        String alternativaC,
        String alternativaD,
        String alternativaE
) {
}