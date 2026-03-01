package br.com.arcnal.arcnal.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "estatisticas_usuarios")
public class EstatisticaUsuario {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assunto_id")
    private Assunto assunto;

    @Column(name = "total_respondidas", nullable = false)
    private Integer totalRespondidas;

    @Column(name = "total_acertos", nullable = false)
    private Integer totalAcertos;

    @Column(name = "total_erros", nullable = false)
    private Integer totalErros;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
}