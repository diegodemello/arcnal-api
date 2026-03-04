package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "temas")
@Data
@RequiredArgsConstructor
public class Tema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Embedded
    private Nome titulo;
    @Column(name = "texto_de_apoio")
    private String textoDeApoio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @Column(name = "criado_em")
    @CreatedDate
    private LocalDateTime criadoEm;
}
