package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.UsuarioResponseDTO;

import java.util.List;

public interface IUsuarioService {
    void cadastrarUsuario(UsuarioRequestDTO dto, String enderecoIp);
    List<UsuarioResponseDTO> listarUsuarios(Integer pagina, Integer objetos);
}
