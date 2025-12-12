package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.entities.Banca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancaDAO extends JpaRepository<Banca, Integer> {
}
