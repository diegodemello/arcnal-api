package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    UserDetails findByEmail(String email);
    Optional<Usuario> findAllByEmail(String email);
}