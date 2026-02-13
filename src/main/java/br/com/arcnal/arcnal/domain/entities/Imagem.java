package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.valueobjects.ArquivoInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagens")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Imagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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