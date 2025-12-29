package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bancas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Banca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private Nome nome;

    public String getNome(){
        return nome.getNome();
    }
}