package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.EmailInvalidoException;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;

    private String endereco;

    public Email(String email) {
        validarSePossuiFormatoValido(email);
        this.endereco = email;
    }

    private void validarSePossuiFormatoValido(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new EmailInvalidoException("Email com formato inválido.");
        }
    }
}
