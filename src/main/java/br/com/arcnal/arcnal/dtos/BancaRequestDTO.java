package br.com.arcnal.arcnal.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BancaRequestDTO(
        @NotBlank(message = "Adicione um nome para a banca examinadora.")
        @Min(value = 1, message = "Digite pelo menos mais de uma letra.")
        String nome
) {
}
