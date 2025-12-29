package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assuntos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;
    @Embedded
    private Nome nome;

    public String getNome(){
        return nome.getNome();
    }
}