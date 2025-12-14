package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.AssuntoReqDTO;
import br.com.arcnal.arcnal.dtos.AssuntoRespDTO;
import br.com.arcnal.arcnal.dtos.AssuntosMateriaRespDTO;

import java.util.List;

public interface IAssuntoService {
    AssuntoRespDTO criarNovoAssunto(AssuntoReqDTO dto);
    AssuntosMateriaRespDTO listarAssuntosPorMateria(Integer idMateria);
}