package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.enums.StatusRedacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "redacoes")
@Data
@RequiredArgsConstructor
public class Redacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany
    @JoinColumn(name = "redacao_id")
    private Tema tema;
    @OneToMany
    @JoinColumn(name = "banca_id")
    private Banca banca;
    @OneToMany
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private StatusRedacao status;
    private String urlRedacao;
    @CreatedDate
    private LocalDateTime criadoEm;
    @LastModifiedDate
    private LocalDateTime atualizadoEm;
}
