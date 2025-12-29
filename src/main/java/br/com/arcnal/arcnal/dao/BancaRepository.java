package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.domain.Banca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancaRepository extends JpaRepository<Banca, Integer> {
    boolean existsByNome(String nome);
}