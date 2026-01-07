package br.com.arcnal.arcnal.application.dto.request;

import java.util.List;

public record AdicionarQuestaoRevisaoRequestDTO(
        List<Integer> idQuestoes
) {
}
