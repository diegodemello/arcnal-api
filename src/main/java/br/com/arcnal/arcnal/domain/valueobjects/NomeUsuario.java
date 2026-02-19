package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.NomeInvalidoException;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class NomeUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;

    public NomeUsuario(String nome) {
        validarTamanho(nome);
        validarSeContemApenasLetras(nome);
        validarSeNomeEstaCompleto(nome);
        this.nome = nome;
    }

    private void validarTamanho(String nome){
        if(nome.length() < 5 || nome.length() > 100){
            throw new NomeInvalidoException("Nome deve ter entre 5 e 100 caracteres.");
        }
    }

    private void validarSeContemApenasLetras(String nome){
        if(!nome.matches("^[A-Za-zÀ-ú ]+$")){
            throw new NomeInvalidoException("Nome deve conter apenas letras e espaços.");
        }
    }

    private void validarSeNomeEstaCompleto(String nome){
        if(!nome.contains(" ")){
            throw new NomeInvalidoException("Nome incompleto. É necessário informar nome e sobrenome.");
        }
    }
}
