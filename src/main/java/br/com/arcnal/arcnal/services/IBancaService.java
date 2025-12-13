package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.BancaRequestDTO;
import br.com.arcnal.arcnal.entities.Banca;

public interface IBancaService {
    Banca adicionarBanca(BancaRequestDTO dto);
}