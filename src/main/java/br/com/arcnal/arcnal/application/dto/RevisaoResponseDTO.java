package br.com.arcnal.arcnal.application.dto;

import java.util.List;
import java.util.UUID;

public record RevisaoResponseDTO(
        UUID id,
        String nome,
        List<QuestaoResponseDTO> questoes
) {
}
