package br.com.arcnal.arcnal.dtos;

import jakarta.validation.constraints.NotBlank;

public record MateriaReqDTO(
        @NotBlank(message = "O nome da matéria não pode ser vazio.")
        String nome
) {
}
