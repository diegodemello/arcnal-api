package br.com.arcnal.arcnal.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank(message = "O nome de usuário não pode ser vazio.")
        String nome,
        @NotBlank(message = "O email não pode ser vazio.")
        String email,
        @NotBlank(message = "A senha não pode ser vazia.")
        String senha
) {
}
