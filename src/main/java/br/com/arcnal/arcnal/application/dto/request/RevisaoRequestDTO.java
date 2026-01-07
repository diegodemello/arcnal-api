package br.com.arcnal.arcnal.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RevisaoRequestDTO(
        @NotBlank(message = "O nome da revisão é obrigatório.")
        String nome,
        @NotNull(message = "A lista de questões não pode ser nula.")
        List<Integer> idQuestoes
) {
}
