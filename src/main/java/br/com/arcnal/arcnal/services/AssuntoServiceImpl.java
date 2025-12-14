package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.AssuntoDAO;
import br.com.arcnal.arcnal.dao.MateriaDAO;
import br.com.arcnal.arcnal.dtos.AssuntoReqDTO;
import br.com.arcnal.arcnal.dtos.AssuntoRespDTO;
import br.com.arcnal.arcnal.dtos.AssuntosMateriaRespDTO;
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
    public AssuntoRespDTO criarNovoAssunto(AssuntoReqDTO dto) {
        Materia materia = materiaDAO.findById(dto.idMateria())
                .orElseThrow(() -> new RuntimeException("Matéria não existe."));

        Assunto assunto = assuntoMapper.assuntoRequestToEntity(dto);
        assunto.setMateria(materia);
        assuntoDAO.save(assunto);

        return assuntoMapper.entityToResponse(assunto);
    }

    @Override
    public AssuntosMateriaRespDTO listarAssuntosPorMateria(Integer idMateria) {
        Materia materia = materiaDAO.findById(idMateria)
                .orElseThrow(() -> new RuntimeException("Esse ID de matéria não existe."));
        List<AssuntoRespDTO> assuntos = assuntoDAO.findAllByMateria_Id(materia.getId())
                .stream()
                .map(assuntoMapper::entityToResponse)
                .toList();

        return new AssuntosMateriaRespDTO(materia.getId(), materia.getNome(), assuntos);
    }
}
