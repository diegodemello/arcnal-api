package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.Banca;

import java.util.List;

public interface IBancaService {
    Banca adicionarBanca(BancaRequestDTO dto);
    List<Banca> listarTodasBancas();
}