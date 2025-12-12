package br.com.arcnal.arcnal.entities;

import br.com.arcnal.arcnal.entities.enums.Cargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    @Column(name = "endereco_ip")
    private String enderecoIp;
    @Enumerated(EnumType.ORDINAL)
    private Cargo cargo;
    private boolean banido;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
