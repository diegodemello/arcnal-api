package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dtos.MateriaReqDTO;
import br.com.arcnal.arcnal.entities.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MateriaServiceImpl implements IMateriaService{

    @Autowired
    MateriaDAO dao;

    @Override
    public Materia criarMateriaSemAssuntos(MateriaReqDTO dto) {
        if(dao.existsByNome(dto.nome())){
            throw new RuntimeException("Erro ao salvar matéria.");
        }
        Materia materia = Materia.builder()
                .nome(dto.nome())
                .build();
        dao.save(materia);
        return materia;
    }
}
