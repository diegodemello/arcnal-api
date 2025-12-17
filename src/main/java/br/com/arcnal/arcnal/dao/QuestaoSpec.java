package br.com.arcnal.arcnal.dao;

import br.com.arcnal.arcnal.domain.Questao;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class QuestaoSpec {
    private static Specification<Questao> porBanca(Integer idBanca) {
        return (root, query, builder) -> {
            if(Objects.nonNull(idBanca)){
                return builder.equal(root.get("banca").get("id"), idBanca);
            }
            return null;
        };
    }

    private static Specification<Questao> porAno(Integer ano) {
        return (root, query, builder) -> {
            if(Objects.nonNull(ano)){
                return builder.equal(root.get("ano"), ano);
            }
            return null;
        };
    }

    private static Specification<Questao> porMateria(Integer idMateria) {
        return (root, query, builder) -> {
            if(Objects.nonNull(idMateria)){
                return builder.equal(root.get("materia").get("id"), idMateria);
            }
            return null;
        };
    }

    private static Specification<Questao> porAssunto(Integer idAssunto) {
        return (root, query, builder) -> {
            if(Objects.nonNull(idAssunto)){
                return builder.equal(root.get("assunto").get("id"), idAssunto);
            }
            return null;
        };
    }

    public static Specification<Questao> porFiltros(Integer idBanca, Integer ano, Integer idMateria, Integer idAssunto) {
        return porBanca(idBanca).and(porAno(ano)).and(porMateria(idMateria)).and(porAssunto(idAssunto));
    }
}
