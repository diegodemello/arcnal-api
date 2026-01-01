package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.NomeInvalidoException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nome {
    private String nome;

    public Nome(String nome) {
        validarNome(nome);
        validarCaracteresEspeciais(nome);
        this.nome = nome;
    }

    private void validarNome(String nome){
        if(nome.length() < 3 || nome.isBlank()){
            throw new NomeInvalidoException("O nome deve conter ao menos 3 caracteres.");
        }
    }

    private void validarCaracteresEspeciais(String nome){
        if(nome.matches(".*[^\\p{L}\\s].*")){
            throw new NomeInvalidoException("O nome não deve conter caracteres especiais.");
        }
    }
}
