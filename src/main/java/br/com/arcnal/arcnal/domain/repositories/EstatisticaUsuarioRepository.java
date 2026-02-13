package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.EstatisticaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstatisticaUsuarioRepository extends JpaRepository<EstatisticaUsuario, Long> {
}
