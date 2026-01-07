package br.com.arcnal.arcnal.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssuntoRequestDTO(
        @NotBlank(message = "O nome do assunto não pode ser vazio.")
        String nome,
        @NotNull
        Integer idMateria
) {
}