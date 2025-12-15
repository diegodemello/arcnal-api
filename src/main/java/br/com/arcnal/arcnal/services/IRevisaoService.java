package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.RevisaoReqDTO;
import br.com.arcnal.arcnal.dtos.RevisaoRespDTO;

import java.util.List;
import java.util.UUID;

public interface IRevisaoService {
    void criarRevisao(RevisaoReqDTO dto);
    List<RevisaoRespDTO> listarRevisoesPorUsuario(UUID idUsuario);
}
