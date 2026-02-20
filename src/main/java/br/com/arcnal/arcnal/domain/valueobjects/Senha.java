package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.SenhaInvalidaException;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class Senha implements Serializable {
    private static final long serialVersionUID = 1L;

    private String senha;

    public Senha(String senha) {
        validarSePossuiEspaco(senha);
        validarTamanho(senha);
        validarSePossuiCaracterEspecial(senha);
        validarSePossuiLetraMaiuscula(senha);
        validarSePossuiNumero(senha);
        this.senha = senha;
    }

    private void validarSePossuiEspaco(String senha){
        if(senha.contains(" ")){
            throw new SenhaInvalidaException("A senha não deve conter espaços em branco.");
        }
    }

    private void validarTamanho(String senha){
        if(senha.length() < 8 || senha.length() > 20){
            throw new SenhaInvalidaException("A senha deve ter entre 8 e 20 caracteres.");
        }
    }

    private void validarSePossuiCaracterEspecial(String senha){
        if(!senha.matches(".*[^a-zA-Z0-9].*")){
            throw new SenhaInvalidaException("A senha deve conter pelo menos um caractere especial.");
        }
    }

    private void validarSePossuiLetraMaiuscula(String senha){
        if(!senha.matches(".*[A-Z].*")){
            throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra maiúscula.");
        }
    }

    private void validarSePossuiNumero(String senha){
        if(!senha.matches(".*\\d.*")){
            throw new SenhaInvalidaException("A senha deve conter pelo menos um número.");
        }
    }
}
