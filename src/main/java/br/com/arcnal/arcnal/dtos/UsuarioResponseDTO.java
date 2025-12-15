package br.com.arcnal.arcnal.dtos;

import br.com.arcnal.arcnal.entities.enums.Cargo;
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
