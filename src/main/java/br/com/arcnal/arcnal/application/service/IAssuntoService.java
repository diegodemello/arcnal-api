package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.dto.AssuntosMateriaResponseDTO;

public interface IAssuntoService {
    AssuntoResponseDTO criarNovoAssunto(AssuntoRequestDTO dto);
    AssuntosMateriaResponseDTO listarAssuntosPorMateria(Integer idMateria);
}