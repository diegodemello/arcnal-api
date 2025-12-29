package br.com.arcnal.arcnal.application.dto;

import java.util.List;

public record AssuntosMateriaResponseDTO(
        Integer id,
        String nome,
        List<AssuntoResponseDTO> assuntos
) {
}
