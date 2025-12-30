package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.SenhaInvalidaException;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class Senha {
    private String senha;

    public Senha(String senha) {
        validarSePossuiEspaco(senha);
        validarTamanho(senha);
        this.senha = senha;
    }

    private void validarSePossuiEspaco(String senha){
        if(senha.contains(" ")){
            throw new SenhaInvalidaException("A senha não deve conter espaços em branco.");
        }
    }

    private void validarTamanho(String senha){
        if(senha.length() < 6 || senha.length() > 20){
            throw new SenhaInvalidaException("A senha deve ter entre 6 e 20 caracteres.");
        }
    }
}
