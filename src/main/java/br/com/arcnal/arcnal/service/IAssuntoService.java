package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.AssuntoRequestDTO;
import br.com.arcnal.arcnal.dto.AssuntoResponseDTO;
import br.com.arcnal.arcnal.dto.AssuntosMateriaResponseDTO;

public interface IAssuntoService {
    AssuntoResponseDTO criarNovoAssunto(AssuntoRequestDTO dto);
    AssuntosMateriaResponseDTO listarAssuntosPorMateria(Integer idMateria);
}