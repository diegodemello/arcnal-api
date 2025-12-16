package br.com.arcnal.arcnal.exception;

public class EmailEmUsoException extends RuntimeException {
    public EmailEmUsoException(String message) {
        super(message);
    }
}