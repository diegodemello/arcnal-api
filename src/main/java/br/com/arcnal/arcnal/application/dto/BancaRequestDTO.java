package br.com.arcnal.arcnal.application.dto;

import jakarta.validation.constraints.NotBlank;

public record BancaRequestDTO(
        @NotBlank(message = "Adicione um nome para a banca examinadora.")
        String nome
) {
}