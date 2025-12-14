package br.com.arcnal.arcnal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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