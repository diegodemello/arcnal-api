package br.com.arcnal.arcnal.domain.entities;

import br.com.arcnal.arcnal.domain.enums.Cargo;
import br.com.arcnal.arcnal.domain.valueobjects.Email;
import br.com.arcnal.arcnal.domain.valueobjects.NomeUsuario;
import br.com.arcnal.arcnal.domain.valueobjects.Senha;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Embedded
    private NomeUsuario nome;
    @Embedded
    @AttributeOverride(name = "endereco", column = @Column(name = "email"))
    private Email email;
    @Embedded
    private Senha senha;
    @Enumerated(EnumType.STRING)
    private Cargo cargo;
    private boolean banido;
    @Column(name = "criado_em")
    @CreatedDate
    private LocalDateTime criadoEm;
    @Column(name = "atualizado_em")
    @LastModifiedDate
    private LocalDateTime atualizadoEm;

    public String getNome() {
        return this.nome.getNome();
    }

    public String getEmail(){
        return this.email.getEndereco();
    }

    public void setSenha(String senha){
        this.senha.setSenha(senha);
    }

    public String getSenha(){
        return this.senha.getSenha();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.cargo == Cargo.ADMIN){
            return List.of(new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("PROFESSOR"),
                    new SimpleGrantedAuthority("USUARIO"));
        }
        if(this.cargo == Cargo.PROFESSOR){
            return List.of(new SimpleGrantedAuthority("PROFESSOR"),
                    new SimpleGrantedAuthority("USUARIO"));
        } else {
            return List.of(new SimpleGrantedAuthority("USUARIO"));
        }
    }

    @Override
    public String getPassword() {
        return senha.getSenha();
    }

    @Override
    public String getUsername() {
        return email.getEndereco();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
