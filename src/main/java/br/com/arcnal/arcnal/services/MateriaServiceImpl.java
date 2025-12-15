package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dtos.MateriaRequestDTO;
import br.com.arcnal.arcnal.entities.Materia;
import br.com.arcnal.arcnal.mapper.MateriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements IMateriaService{

    private final MateriaDAO dao;
    private final AssuntoDAO assuntoDAO;
    private final MateriaMapper materiaMapper;

    @Override
    public Materia criarMateriaSemAssuntos(MateriaRequestDTO dto) {
        if(dao.existsByNome(dto.nome())){
            throw new RuntimeException("Já existe uma matéria com esse nome.");
        }
        return dao.save(materiaMapper.toEntity(dto));
    }
}
