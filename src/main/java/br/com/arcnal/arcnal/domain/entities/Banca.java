package br.com.arcnal.arcnal.domain.entities;

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
    private String nome;
}