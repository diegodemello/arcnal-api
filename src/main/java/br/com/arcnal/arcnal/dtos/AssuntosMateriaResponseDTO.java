package br.com.arcnal.arcnal.dtos;

import java.util.List;

public record AssuntosMateriaResponseDTO(
        Integer id,
        String nome,
        List<AssuntoResponseDTO> assuntos
) {
}
