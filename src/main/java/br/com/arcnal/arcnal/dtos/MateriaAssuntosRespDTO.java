package br.com.arcnal.arcnal.dtos;

import br.com.arcnal.arcnal.entities.Assunto;
import lombok.Builder;

import java.util.List;

@Builder
public record MateriaAssuntosRespDTO(
        String nome,
        List<AssuntoRespDTO> assuntos
) {
}
