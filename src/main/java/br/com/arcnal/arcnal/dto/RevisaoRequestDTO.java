package br.com.arcnal.arcnal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record RevisaoRequestDTO(
        @NotBlank(message = "O nome da revisão é obrigatório.")
        String nome,
        UUID idUsuario,
        @NotNull(message = "A lista de questões não pode ser nula.")
        List<Integer> idQuestoes
) {
}
