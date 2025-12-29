package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.domain.entities.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Banca b WHERE b.nome.nome = :nome")
    boolean existsByNome(@Param("nome") String nome);
    List<Assunto> findAllByMateria_Id(Integer idMateria);
}
