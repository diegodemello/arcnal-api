package br.com.arcnal.arcnal.application.dto.request;

public record RedefinirSenhaRequestDTO(
        String token,
        String senha
) {
}
