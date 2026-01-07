package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM usuarios WHERE email = ?1", nativeQuery = true)
    boolean existsByEmail(String email);
    Optional<UserDetails> findByEmailEndereco(String email);
    Optional<Usuario> findAllByEmail(String email);
    Optional<Usuario> findAllByEmailEndereco(String email);
}