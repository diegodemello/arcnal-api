package br.com.arcnal.arcnal.presentation.exception;

import java.time.Instant;

public record ExceptionResponse(
        Integer codigo,
        String mensagem,
        Instant datahora
) {
}
