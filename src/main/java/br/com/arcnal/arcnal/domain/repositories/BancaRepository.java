package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Banca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancaRepository extends JpaRepository<Banca, Integer> {
    boolean existsByNome(String nome);
}