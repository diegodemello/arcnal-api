package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.AssuntoReqDTO;
import br.com.arcnal.arcnal.entities.Assunto;

public interface IAssuntoService {
    Assunto criarNovoAssunto(AssuntoReqDTO dto);
}