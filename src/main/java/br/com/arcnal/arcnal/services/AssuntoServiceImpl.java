package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dtos.AssuntoRequestDTO;
import br.com.arcnal.arcnal.dtos.AssuntoResponseDTO;
import br.com.arcnal.arcnal.dtos.AssuntosMateriaResponseDTO;
import br.com.arcnal.arcnal.entities.Assunto;
import br.com.arcnal.arcnal.entities.Materia;
import br.com.arcnal.arcnal.mapper.AssuntoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssuntoServiceImpl implements IAssuntoService{

    private final AssuntoDAO assuntoDAO;
    private final MateriaDAO materiaDAO;
    private final AssuntoMapper assuntoMapper;

    @Override
    public AssuntoResponseDTO criarNovoAssunto(AssuntoRequestDTO dto) {
        Materia materia = buscarMateriaPorId(dto.idMateria());

        Assunto assunto = assuntoMapper.toEntity(dto);
        assunto.setMateria(materia);
        assuntoDAO.save(assunto);

        return assuntoMapper.toResponse(assunto);
    }

    @Override
    public AssuntosMateriaResponseDTO listarAssuntosPorMateria(Integer idMateria) {
        Materia materia = buscarMateriaPorId(idMateria);

        List<AssuntoResponseDTO> assuntos = assuntoDAO.findAllByMateria_Id(materia.getId())
                .stream()
                .map(assuntoMapper::toResponse)
                .toList();

        return new AssuntosMateriaResponseDTO(materia.getId(), materia.getNome(), assuntos);
    }

    public Materia buscarMateriaPorId(Integer idMateria){
        return materiaDAO.findById(idMateria)
                .orElseThrow(() -> new RuntimeException("Matéria não existe."));
    }
}
