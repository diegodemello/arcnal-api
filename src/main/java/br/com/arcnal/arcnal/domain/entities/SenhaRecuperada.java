package br.com.arcnal.arcnal.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "senha_recuperada")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SenhaRecuperada {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    private String token;
    private boolean utilizado;
    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;
}
