package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.entities.EstatisticaUsuario;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EstatisticaUsuarioRepository extends JpaRepository<EstatisticaUsuario, Long> {
    Optional<EstatisticaUsuario> findByUsuarioAndMateriaAndAssunto(
            Usuario usuario,
            Materia materia,
            Assunto assunto
    );
}
