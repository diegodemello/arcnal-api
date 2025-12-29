package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.enums.Nivel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "questoes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_banca")
    private Banca banca;
    @ManyToOne
    @JoinColumn(name = "id_materia")
    private Materia materia;
    @ManyToOne
    @JoinColumn(name = "id_assunto")
    private Assunto assunto;
    private Integer ano;
    private String enunciado;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;
    @Column(name = "alt_a")
    private String alternativaA;
    @Column(name = "alt_b")
    private String alternativaB;
    @Column(name = "alt_c")
    private String alternativaC;
    @Column(name = "alt_d")
    private String alternativaD;
    @Column(name = "alt_e")
    private String alternativaE;
    @Column(name = "alt_correta")
    private Character alternativaCorreta;
    @Column(name = "texto_correcao")
    private String textoCorrecao;
    @Column(name = "video_correcao")
    private String videoCorrecao;
    @Column(name = "criado_em")
    @CreatedDate
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    @LastModifiedDate
    private LocalDateTime atualizadoEm;
}
