package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.request.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntosMateriaResponseDTO;

public interface IAssuntoService {
    AssuntoResponseDTO criarNovoAssunto(AssuntoRequestDTO dto);
    AssuntosMateriaResponseDTO listarAssuntosPorMateria(Integer idMateria);
}