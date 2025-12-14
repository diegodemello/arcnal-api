package br.com.arcnal.arcnal.dtos;

import lombok.Builder;

@Builder
public record AssuntoRespDTO(
        Integer id,
        String nome
) {
}
