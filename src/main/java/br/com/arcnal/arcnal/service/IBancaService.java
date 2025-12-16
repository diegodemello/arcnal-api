package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.Banca;

public interface IBancaService {
    Banca adicionarBanca(BancaRequestDTO dto);
}