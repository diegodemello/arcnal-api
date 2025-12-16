package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.Materia;

public interface IMateriaService {
    Materia criarMateriaSemAssuntos(MateriaRequestDTO dto);
}