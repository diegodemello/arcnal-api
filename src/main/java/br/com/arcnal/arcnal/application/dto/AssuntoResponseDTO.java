package br.com.arcnal.arcnal.application.dto;

import lombok.Builder;

@Builder
public record AssuntoResponseDTO(
        Integer id,
        String nome
) {
}
