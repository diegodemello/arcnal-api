package br.com.arcnal.arcnal.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "respostas_usuarios",
        indexes = {
                @Index(name = "idx_usuario_data", columnList = "usuario_id, data_resposta DESC"),
                @Index(name = "idx_usuario_materia", columnList = "usuario_id, materia_id, acertou")
        }
)
@Getter
@Setter
@NoArgsConstructor
public class RespostaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questao_id", nullable = false)
    private Questao questao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assunto_id", nullable = false)
    private Assunto assunto;
    private boolean acertou;
    @Column(name = "data_resposta", nullable = false)
    private LocalDate dataResposta;
}
