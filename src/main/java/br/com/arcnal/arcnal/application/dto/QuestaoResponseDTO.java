package br.com.arcnal.arcnal.application.dto;

import br.com.arcnal.arcnal.domain.enums.Nivel;
import lombok.Builder;

@Builder
public record QuestaoResponseDTO(
        Integer id,
        String banca,
        String materia,
        String assunto,
        Nivel nivel,
        Integer ano,
        String enunciado,
        String alternativaA,
        String alternativaB,
        String alternativaC,
        String alternativaD,
        String alternativaE
) {
}