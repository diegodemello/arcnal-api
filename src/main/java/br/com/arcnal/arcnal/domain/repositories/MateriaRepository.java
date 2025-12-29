package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
    boolean existsByNome(String nome);
    boolean existsById(Integer id);
}
