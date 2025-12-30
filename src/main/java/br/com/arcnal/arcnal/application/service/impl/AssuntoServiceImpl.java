package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.IAssuntoService;
import br.com.arcnal.arcnal.domain.repositories.AssuntoRepository;
import br.com.arcnal.arcnal.domain.repositories.MateriaRepository;
import br.com.arcnal.arcnal.application.dto.request.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntosMateriaResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.exception.MateriaNaoEncontradaException;
import br.com.arcnal.arcnal.application.mapper.AssuntoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssuntoServiceImpl implements IAssuntoService {

    private final AssuntoRepository assuntoRepository;
    private final MateriaRepository materiaRepository;
    private final AssuntoMapper assuntoMapper;

    @Override
    public AssuntoResponseDTO criarNovoAssunto(AssuntoRequestDTO dto) {
        Materia materia = buscarMateriaPorId(dto.idMateria());

        Assunto assunto = assuntoMapper.toEntity(dto);
        assunto.setMateria(materia);
        assuntoRepository.save(assunto);

        return assuntoMapper.toResponse(assunto);
    }

    @Override
    public AssuntosMateriaResponseDTO listarAssuntosPorMateria(Integer idMateria) {
        Materia materia = buscarMateriaPorId(idMateria);

        List<AssuntoResponseDTO> assuntos = assuntoRepository.findAllByMateria_Id(materia.getId())
                .stream()
                .map(assuntoMapper::toResponse)
                .toList();

        return new AssuntosMateriaResponseDTO(materia.getId(), materia.getNome(), assuntos);
    }

    public Materia buscarMateriaPorId(Integer idMateria){
        return materiaRepository.findById(idMateria)
                .orElseThrow(() -> new MateriaNaoEncontradaException("Matéria com ID " + idMateria + " não encontrada."));
    }
}
