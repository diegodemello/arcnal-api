package br.com.arcnal.arcnal.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MateriaReqDTO(
        @NotBlank(message = "O nome da matéria não pode ser vazio.")
        @Min(value = 4, message = "O nome da matéria precisa ter no mínimo 4 caracteres.")
        String nome
) {
}
