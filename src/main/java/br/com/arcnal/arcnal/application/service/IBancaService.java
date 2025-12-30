package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.request.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Banca;

import java.util.List;

public interface IBancaService {
    Banca adicionarBanca(BancaRequestDTO dto);
    List<Banca> listarTodasBancas();
}