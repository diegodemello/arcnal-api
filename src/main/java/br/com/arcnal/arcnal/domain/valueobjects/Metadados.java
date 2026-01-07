package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.MetadadosInvalidoException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Metadados {
    private Integer ano;
    private String enunciado;

    @Transient
    private Integer ANO_ATUAL = LocalDate.now().getYear();
    @Transient
    private Integer TAMANHO_MINIMO_ENUNCIADO = 10;
    @Transient
    private Integer ANO_MINIMO = 1950;

    @PreUpdate
    @PrePersist
    private void validar(){
        validarSeAnoEValido(ano);
        validarEnunciado(enunciado);
    }

    private void validarSeAnoEValido(Integer ano){
        if (ano == null || ano < ANO_MINIMO){
            throw new MetadadosInvalidoException("Ano da questão inválido.");
        }
        if(ano > ANO_ATUAL){
            throw new MetadadosInvalidoException("Ano da questão não pode ser maior que o ano atual.");
        }
    }

    private void validarEnunciado(String enunciado){
        if(enunciado == null || enunciado.isBlank()){
            throw new MetadadosInvalidoException("Enunciado da questão não pode ser nulo ou vazio.");
        }
        if(enunciado.length() < TAMANHO_MINIMO_ENUNCIADO){
            throw new MetadadosInvalidoException("Enunciado da questão deve ter no mínimo 10 caracteres.");
        }
    }
}
