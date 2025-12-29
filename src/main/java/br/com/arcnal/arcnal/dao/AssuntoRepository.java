package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.domain.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {
    boolean existsByNome(String nome);
    List<Assunto> findAllByMateria_Id(Integer idMateria);
}
