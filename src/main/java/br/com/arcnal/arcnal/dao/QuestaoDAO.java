package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.entities.Questao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestaoDAO extends JpaRepository<Questao, Integer> {
}
