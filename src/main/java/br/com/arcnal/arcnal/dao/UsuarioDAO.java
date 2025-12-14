package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioDAO extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
}
