package br.com.arcnal.arcnal.dtos;

import java.util.List;

public record AssuntosMateriaRespDTO(
        Integer id,
        String nome,
        List<AssuntoRespDTO> assuntos
) {
}
