package br.com.arcnal.arcnal.application.dto.response;

import lombok.Builder;

@Builder
public record AssuntoResponseDTO(
        Integer id,
        String nome
) {
}
