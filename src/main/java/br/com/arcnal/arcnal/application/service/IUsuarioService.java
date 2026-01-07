package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.request.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.UsuarioResponseDTO;

import java.util.List;

public interface IUsuarioService {
    void cadastrarUsuario(UsuarioRequestDTO dto);
    List<UsuarioResponseDTO> listarUsuarios(Integer pagina, Integer objetos);
}
