package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.EmailInvalidoException;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Email {
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
