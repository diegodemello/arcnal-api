package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Materia;

import java.util.List;

public interface IMateriaService {
    Materia criarMateriaSemAssuntos(MateriaRequestDTO dto);
    List<Materia> listarTodasMaterias();
}