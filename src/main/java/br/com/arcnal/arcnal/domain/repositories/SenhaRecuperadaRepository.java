package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.SenhaRecuperada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SenhaRecuperadaRepository extends JpaRepository<SenhaRecuperada, UUID> {
    Optional<SenhaRecuperada> findByToken(String token);
    List<SenhaRecuperada> findAllByUsuarioId(UUID usuarioId);
}
