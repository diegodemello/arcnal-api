package br.com.arcnal.arcnal.dtos;

import java.util.List;
import java.util.UUID;

public record RevisaoRespDTO(
        UUID id,
        String nome,
        List<QuestaoRespDTO> questoes
) {
}
