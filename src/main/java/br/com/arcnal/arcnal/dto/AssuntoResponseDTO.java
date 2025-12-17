package br.com.arcnal.arcnal.dto;

import lombok.Builder;

@Builder
public record AssuntoResponseDTO(
        Integer id,
        String nome
) {
}
