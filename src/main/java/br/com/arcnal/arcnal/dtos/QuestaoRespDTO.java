package br.com.arcnal.arcnal.dtos;

import br.com.arcnal.arcnal.entities.enums.Nivel;
import lombok.Builder;

@Builder
public record QuestaoRespDTO(
        Integer id,
        Nivel nivel,
        String enunciado,
        String alternativaA,
        String alternativaB,
        String alternativaC,
        String alternativaD,
        String alternativaE
) {
}