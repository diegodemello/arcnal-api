package br.com.arcnal.arcnal.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "questoes")
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
