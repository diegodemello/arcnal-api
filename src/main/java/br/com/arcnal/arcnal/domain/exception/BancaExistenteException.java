package br.com.arcnal.arcnal.domain.exception;

public class BancaExistenteException extends RuntimeException {
    public BancaExistenteException(String message) {
        super(message);
    }
}
