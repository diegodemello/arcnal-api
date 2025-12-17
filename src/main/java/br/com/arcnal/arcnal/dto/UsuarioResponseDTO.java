package br.com.arcnal.arcnal.dto;

import br.com.arcnal.arcnal.domain.enums.Cargo;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UsuarioResponseDTO(
        UUID id,
        String nome,
        String email,
        boolean banido,
        Cargo cargo
) {
}
