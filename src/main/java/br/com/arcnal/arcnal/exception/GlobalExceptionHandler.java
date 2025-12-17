package br.com.arcnal.arcnal.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if(ex.getCause() instanceof InvalidFormatException){
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if(cause.getTargetType().equals(Character.class)){
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Tipo inválido para alternativa correta. Deve ser um único caractere (A, B, C, D ou E).",
                        Instant.now()
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
            }
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                "Erro na leitura da mensagem HTTP: ", Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(EmailEmUsoException.class)
    public ResponseEntity<ExceptionResponse> handleEmailEmUsoException(EmailEmUsoException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(MateriaNaoEncontradaException.class)
    public ResponseEntity<ExceptionResponse> handleMateriaNaoEncoontrada(MateriaNaoEncontradaException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(AssuntoNaoEncontradoException.class)
    public ResponseEntity<ExceptionResponse> handleAssuntoNaoEncontrado(AssuntoNaoEncontradoException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ExceptionResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(QuestaoNaoEncontradaException.class)
    public ResponseEntity<ExceptionResponse> handleQuestaoNaoEncontrada(QuestaoNaoEncontradaException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(RevisoesExistentesException.class)
    public ResponseEntity<ExceptionResponse> handleRevisoesExistentes(RevisoesExistentesException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(BancaExistenteException.class)
    public ResponseEntity<ExceptionResponse> handleBancaExistente(BancaExistenteException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(MateriaExistenteException.class)
    public ResponseEntity<ExceptionResponse> handleMateriaExistente(MateriaExistenteException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(EnunciadoExistenteException.class)
    public ResponseEntity<ExceptionResponse> handleEnunciadoExistente(EnunciadoExistenteException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.CONFLICT.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }

    @ExceptionHandler(AnoInvalidoException.class)
    public ResponseEntity<ExceptionResponse> handleAnoInvalido(AnoInvalidoException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(AlternativaInvalidaException.class)
    public ResponseEntity<ExceptionResponse> handleAnoInvalido(AlternativaInvalidaException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }
}