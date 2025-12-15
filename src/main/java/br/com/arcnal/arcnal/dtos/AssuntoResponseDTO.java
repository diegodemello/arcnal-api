package br.com.arcnal.arcnal.dtos;

import lombok.Builder;

@Builder
public record AssuntoResponseDTO(
        Integer id,
        String nome
) {
}
