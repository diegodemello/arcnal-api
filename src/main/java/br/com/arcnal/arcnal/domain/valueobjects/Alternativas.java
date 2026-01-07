package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.AlternativaInvalidaException;
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
public class Alternativas {
    private String alternativaA;
    private String alternativaB;
    private String alternativaC;
    private String alternativaD;
    private String alternativaE;
    private Character alternativaCorreta;

    @PrePersist
    @PreUpdate
    private void validar(){
        validarSeAlternativaAeBForamPreenchdias(alternativaA, alternativaB);
        validarAlternativaCorreta(alternativaCorreta, alternativaC, alternativaD, alternativaE);
    }

    private void validarSeAlternativaAeBForamPreenchdias(String alternativaA, String alternativaB){
        if(alternativaA == null && alternativaB == null){
            throw new AlternativaInvalidaException("Alternativas A e B devem ser preenchidas.");
        }
    }

    private void validarAlternativaCorreta(Character alternativaCorreta, String alternativaC,
                                           String alternativaD,
                                           String alternativaE) {
        if ((alternativaCorreta == 'C' && (alternativaC == null || alternativaC.isEmpty())) ||
                (alternativaCorreta == 'D' && (alternativaD == null || alternativaD.isEmpty())) ||
                (alternativaCorreta == 'E' && (alternativaE == null || alternativaE.isEmpty()))) {
            throw new AlternativaInvalidaException("A alternativa correta deve corresponder a uma alternativa preenchdia.");
        }
    }
}
