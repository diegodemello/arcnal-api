package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.IMateriaService;
import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.request.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.exception.MateriaExistenteException;
import br.com.arcnal.arcnal.application.mapper.MateriaMapper;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements IMateriaService {

    private final MateriaRepository dao;
    private final MateriaMapper materiaMapper;

    @Qualifier("materiasCriadas")
    private final Counter materiasCriadas;

    @Override
    public Materia criarMateriaSemAssuntos(MateriaRequestDTO dto) {
        validarSeJaExisteMateriaComMesmoNome(dto.nome());
        materiasCriadas.increment();
        return dao.save(materiaMapper.toEntity(dto));
    }

    @Override
    public List<Materia> listarTodasMaterias() {
        return dao.findAll();
    }

    private void validarSeJaExisteMateriaComMesmoNome(String nome){
        if(dao.existsByNome(nome)){
            throw new MateriaExistenteException("Matéria com o nome " + nome + " já existe.");
        }
    }
}
