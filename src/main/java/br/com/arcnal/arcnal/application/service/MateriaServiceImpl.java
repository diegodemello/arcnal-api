package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.exception.MateriaExistenteException;
import br.com.arcnal.arcnal.application.mapper.MateriaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements IMateriaService{

    private final MateriaRepository dao;
    private final MateriaMapper materiaMapper;

    @Override
    public Materia criarMateriaSemAssuntos(MateriaRequestDTO dto) {
        if(dao.existsByNome(dto.nome())){
            throw new MateriaExistenteException("Matéria com o nome " + dto.nome() + " já existe.");
        }
        return dao.save(materiaMapper.toEntity(dto));
    }

    @Override
    public List<Materia> listarTodasMaterias() {
        return dao.findAll();
    }
}
