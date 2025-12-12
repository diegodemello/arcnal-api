package br.com.arcnal.arcnal.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "assuntos")
@Getter
@Setter
public class Assunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;
    private String nome;
}