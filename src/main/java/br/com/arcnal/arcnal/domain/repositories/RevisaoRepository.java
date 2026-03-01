package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Revisao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RevisaoRepository extends JpaRepository<Revisao, UUID> {
    List<Revisao> findAllByUsuarioId(UUID idUsuario);
    Long countByUsuarioId(UUID idUsuario);
}
