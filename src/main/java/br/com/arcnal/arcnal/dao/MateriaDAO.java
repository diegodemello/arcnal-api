package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaDAO extends JpaRepository<Materia, Integer> {
    boolean existsByNome(String nome);
}
