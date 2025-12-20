package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.dto.UsuarioResponseDTO;

import java.util.List;

public interface IUsuarioService {
    void cadastrarUsuario(UsuarioRequestDTO dto, String enderecoIp);
    List<UsuarioResponseDTO> listarUsuarios(Integer pagina, Integer objetos);
}
