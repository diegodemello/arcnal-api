package br.com.arcnal.arcnal.application.dto;

import jakarta.validation.constraints.NotBlank;

public record BancaRequestDTO(
        String nome
) {
}