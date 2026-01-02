package br.com.arcnal.arcnal.application.dto.response;

import java.util.List;
import java.util.UUID;

public record RevisaoResponseDTO(
        UUID id,
        String nome,
        Integer numeroDeQuestoes
) {
}
