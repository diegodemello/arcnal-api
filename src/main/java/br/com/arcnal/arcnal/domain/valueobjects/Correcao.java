package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.CorrecaoInvalidaException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Correcao {
    private String textoCorrecao;
    private String videoCorrecao;

    @PrePersist
    @PreUpdate
    private void validar(){
        validarNulo(textoCorrecao);
        validarNulo(videoCorrecao);
        validarTamanhoDaCorrecao(textoCorrecao);
        validarSeEURL(videoCorrecao);
    }

    private void validarNulo(String texto){
        if(texto == null || texto.isBlank()){
            throw new CorrecaoInvalidaException("O texto da correção não pode ser nulo ou vazio.");
        }
    }

    private void validarTamanhoDaCorrecao(String texto){
        if(texto.length() < 20){
            throw new CorrecaoInvalidaException("O texto da correção deve ter no mínimo 20 caracteres.");
        }
    }

    private void validarSeEURL(String texto){
        if(!texto.startsWith("http://") && !texto.startsWith("https://")){
            throw new CorrecaoInvalidaException("O texto da correção deve ser uma URL válida.");
        }
    }
}
