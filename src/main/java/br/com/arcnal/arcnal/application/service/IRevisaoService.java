package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.DetalheRevisaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;

import java.util.List;
import java.util.UUID;

public interface IRevisaoService {
    void criarRevisao(RevisaoRequestDTO dto, String emailUsuario);
    List<RevisaoResponseDTO> listarRevisoesPorUsuario(UUID idUsuario);
    DetalheRevisaoResponseDTO listarRevisao(Integer pagina, Integer objetos, UUID idRevisao);
}
