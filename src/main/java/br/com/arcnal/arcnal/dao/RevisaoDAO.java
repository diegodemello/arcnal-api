package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.domain.Revisao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RevisaoDAO extends JpaRepository<Revisao, UUID> {
    List<Revisao> findAllByUsuarioId(UUID idUsuario);
}
