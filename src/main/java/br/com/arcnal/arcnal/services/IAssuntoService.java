package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.AssuntoRequestDTO;
import br.com.arcnal.arcnal.dtos.AssuntoResponseDTO;
import br.com.arcnal.arcnal.dtos.AssuntosMateriaResponseDTO;

public interface IAssuntoService {
    AssuntoResponseDTO criarNovoAssunto(AssuntoRequestDTO dto);
    AssuntosMateriaResponseDTO listarAssuntosPorMateria(Integer idMateria);
}