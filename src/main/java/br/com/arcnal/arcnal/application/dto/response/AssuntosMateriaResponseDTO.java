package br.com.arcnal.arcnal.application.dto.response;

import java.util.List;

public record AssuntosMateriaResponseDTO(
        Integer id,
        String nome,
        List<AssuntoResponseDTO> assuntos
) {
}
