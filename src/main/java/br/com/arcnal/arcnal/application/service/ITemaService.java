package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.request.TemaRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.TemaResponseDTO;

import java.util.List;

public interface ITemaService {
    void criarTema(TemaRequestDTO request);
    List<TemaResponseDTO> listarTemas();
    void deletarTema(Integer idTema);
}
