package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.AssuntoRepository;
import br.com.arcnal.arcnal.dao.MateriaRepository;
import br.com.arcnal.arcnal.dto.AssuntoRequestDTO;
import br.com.arcnal.arcnal.dto.AssuntoResponseDTO;
import br.com.arcnal.arcnal.dto.AssuntosMateriaResponseDTO;
import br.com.arcnal.arcnal.domain.Assunto;
import br.com.arcnal.arcnal.domain.Materia;
import br.com.arcnal.arcnal.exception.MateriaNaoEncontradaException;
import br.com.arcnal.arcnal.mapper.AssuntoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssuntoServiceImpl implements IAssuntoService{

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
