package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.AssuntoReqDTO;
import br.com.arcnal.arcnal.dtos.AssuntoRespDTO;

public interface IAssuntoService {
    AssuntoRespDTO criarNovoAssunto(AssuntoReqDTO dto);
}