package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.RespostaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaUsuarioRepository extends JpaRepository<RespostaUsuario, Long> {
}
