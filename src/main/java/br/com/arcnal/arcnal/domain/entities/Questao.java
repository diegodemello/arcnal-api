package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.enums.Nivel;
import br.com.arcnal.arcnal.domain.valueobjects.Alternativas;
import br.com.arcnal.arcnal.domain.valueobjects.Correcao;
import br.com.arcnal.arcnal.domain.valueobjects.Metadados;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @AttributeOverride(name = "ano", column = @Column(name = "ano"))
    @AttributeOverride(name = "enunciado", column = @Column(name = "enunciado"))
    @Embedded
    private Metadados metadados;
    @OneToMany(mappedBy = "questao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;
    @AttributeOverride(name = "alternativaA", column = @Column(name = "alt_a"))
    @AttributeOverride(name = "alternativaB", column = @Column(name = "alt_b"))
    @AttributeOverride(name = "alternativaC", column = @Column(name = "alt_c"))
    @AttributeOverride(name = "alternativaD", column = @Column(name = "alt_d"))
    @AttributeOverride(name = "alternativaE", column = @Column(name = "alt_e"))
    @AttributeOverride(name = "alternativaCorreta", column = @Column(name = "alt_correta"))
    @Embedded
    private Alternativas alternativas;
    @AttributeOverride(name = "textoCorrecao", column = @Column(name = "texto_correcao"))
    @AttributeOverride(name = "videoCorrecao", column = @Column(name = "video_correcao"))
    @Embedded
    private Correcao correcao;
    @Column(name = "criado_em")
    @CreatedDate
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    @LastModifiedDate
    private LocalDateTime atualizadoEm;

    public Character getAlternativaCorreta() {
        return this.alternativas.getAlternativaCorreta();
    }

    public String getAlternativaA(){
        return this.alternativas.getAlternativaA();
    }

    public String getAlternativaB(){
        return this.alternativas.getAlternativaB();
    }

    public String getAlternativaC(){
        return this.alternativas.getAlternativaC();
    }

    public String getAlternativaD(){
        return this.alternativas.getAlternativaD();
    }

    public String getAlternativaE(){
        return this.alternativas.getAlternativaE();
    }
}
