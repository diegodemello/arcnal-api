package br.com.arcnal.arcnal.dtos;

import jakarta.validation.constraints.NotBlank;

public record BancaRequestDTO(
        @NotBlank(message = "Adicione um nome para a banca examinadora.")
        String nome
) {
}