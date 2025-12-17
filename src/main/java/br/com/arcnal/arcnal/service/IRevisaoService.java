package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.RevisaoRequestDTO;
import br.com.arcnal.arcnal.dto.RevisaoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IRevisaoService {
    void criarRevisao(RevisaoRequestDTO dto);
    List<RevisaoResponseDTO> listarRevisoesPorUsuario(UUID idUsuario);
}
