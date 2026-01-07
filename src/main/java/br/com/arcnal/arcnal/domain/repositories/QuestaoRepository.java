package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestaoRepository extends JpaRepository<Questao, Integer>, JpaSpecificationExecutor<Questao> {
    boolean existsByMetadadosEnunciado(String enunciado);
}
