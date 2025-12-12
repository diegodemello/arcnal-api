package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.entities.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssuntoDAO extends JpaRepository<Assunto, Integer> {
}
