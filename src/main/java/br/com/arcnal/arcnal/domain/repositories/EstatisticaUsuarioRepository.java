package br.com.arcnal.arcnal.domain.repositories;

import br.com.arcnal.arcnal.application.dto.response.DadosGeraisEstatisticaResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.DesempenhoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.entities.EstatisticaUsuario;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstatisticaUsuarioRepository extends JpaRepository<EstatisticaUsuario, Long> {
    Optional<EstatisticaUsuario> findByUsuarioAndMateriaAndAssunto(
            Usuario usuario,
            Materia materia,
            Assunto assunto
    );


    @Query("""
    SELECT new br.com.arcnal.arcnal.application.dto.response.DadosGeraisEstatisticaResponseDTO(
            CAST(COALESCE(SUM(e.totalRespondidas), 0L) AS long),
            CAST(COALESCE(SUM(e.totalAcertos), 0L) AS long),
            CAST(COALESCE(SUM(e.totalErros), 0L) AS long) 
        )
        FROM EstatisticaUsuario e
        WHERE e.usuario.id = :usuario
    """)
    DadosGeraisEstatisticaResponseDTO buscarResumoGeral(UUID usuario);

    @Query("""
        SELECT COUNT(DISTINCT e.materia.id)
        FROM EstatisticaUsuario e
        WHERE e.usuario.id = :usuario
    """)
    Long materiasEstudadas(UUID usuario);

    @Query("""
        SELECT new br.com.arcnal.arcnal.application.dto.response.DesempenhoResponseDTO(
            m.nome.nome,
            CAST(COALESCE(SUM(e.totalAcertos), 0) AS long),
            CAST(COALESCE(SUM(e.totalErros), 0) AS long) 
        )
        FROM EstatisticaUsuario e
        JOIN e.materia m
        WHERE e.usuario.id = :usuario
        GROUP BY m.nome
    """)
    List<DesempenhoResponseDTO> buscarDesempenhoPorMateria(UUID usuario);

    @Query("""
    SELECT new br.com.arcnal.arcnal.application.dto.response.DesempenhoResponseDTO(
            a.nome.nome,
            CAST(COALESCE(SUM(e.totalAcertos), 0) AS long) ,
            CAST(COALESCE(SUM(e.totalErros), 0) AS long) 
        )
        FROM EstatisticaUsuario e
        JOIN e.assunto a
        WHERE e.usuario.id = :usuario
        GROUP BY a.nome
    """)
    List<DesempenhoResponseDTO> buscarDesempenhoPorAssunto(UUID usuario);

}
