package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.domain.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestaoRepository extends JpaRepository<Questao, Integer>, JpaSpecificationExecutor<Questao> {
    boolean existsByEnunciado(String enunciado);
}
