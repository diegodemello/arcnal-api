package br.com.arcnal.arcnal.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "revisoes")
@Getter
@Setter
public class Revisao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @ManyToMany
    @JoinTable(name = "revisoes_questoes",
            joinColumns = @JoinColumn(name = "id_revisao"),
            inverseJoinColumns = @JoinColumn(name = "id_questao"))
    private List<Questao> questoes;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
