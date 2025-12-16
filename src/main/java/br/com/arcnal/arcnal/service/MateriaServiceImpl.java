package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dto.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.Materia;
import br.com.arcnal.arcnal.exception.MateriaExistenteException;
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
            throw new MateriaExistenteException("Matéria com o nome " + dto.nome() + " já existe.");
        }
        return dao.save(materiaMapper.toEntity(dto));
    }
}
