package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.valueobjects.ArquivoInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagens")
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Embedded
    private ArquivoInfo arquivoInfo;
    @ManyToOne
    @JoinColumn(name = "id_questao")
    private Questao questao;
    @CreatedDate
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @LastModifiedDate
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}