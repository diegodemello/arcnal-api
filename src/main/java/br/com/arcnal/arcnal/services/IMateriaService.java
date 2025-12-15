package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.MateriaRequestDTO;
import br.com.arcnal.arcnal.entities.Materia;

public interface IMateriaService {
    Materia criarMateriaSemAssuntos(MateriaRequestDTO dto);
}