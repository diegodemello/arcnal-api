package br.com.arcnal.arcnal.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "estatisticas_usuarios",
        indexes = {
                @Index(name = "idx_usuario_periodo", columnList = "usuario_id, periodo"),
                @Index(name = "idx_usuario_materia_periodo", columnList = "usuario_id, materia_id, periodo")
        }
)
public class EstatisticaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assunto_id")
    private Assunto assunto;

    @Column(nullable = false, length = 20)
    private String periodo;

    @Column(name = "total_respondidas", nullable = false)
    private Integer totalRespondidas;

    @Column(name = "total_acertos", nullable = false)
    private Integer totalAcertos;

    @Column(name = "total_erros", nullable = false)
    private Integer totalErros;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;
}
