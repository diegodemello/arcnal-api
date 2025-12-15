package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.RevisaoRequestDTO;
import br.com.arcnal.arcnal.dtos.RevisaoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IRevisaoService {
    void criarRevisao(RevisaoRequestDTO dto);
    List<RevisaoResponseDTO> listarRevisoesPorUsuario(UUID idUsuario);
}
