package br.com.arcnal.arcnal.domain;

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
    private String nome;
}