package br.com.arcnal.arcnal.domain;

import br.com.arcnal.arcnal.domain.enums.Cargo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    @Column(name = "endereco_ip")
    private String enderecoIp;
    @Enumerated(EnumType.STRING)
    private Cargo cargo;
    private boolean banido;
    @Column(name = "criado_em")
    @CreatedDate
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    @LastModifiedDate
    private LocalDateTime atualizadoEm;
}
