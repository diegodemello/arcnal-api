package br.com.arcnal.arcnal.application.dto.response;

import br.com.arcnal.arcnal.domain.enums.Nivel;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestaoResponseDTO(
        Integer id,
        String banca,
        String materia,
        String assunto,
        List<ImagemResponseDTO> imagens,
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