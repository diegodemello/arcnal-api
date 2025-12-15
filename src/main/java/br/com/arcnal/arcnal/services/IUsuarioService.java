package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.UsuarioRequestDTO;
import br.com.arcnal.arcnal.dtos.UsuarioResponseDTO;

import java.util.List;

public interface IUsuarioService {
    void cadastrarUsuario(UsuarioRequestDTO dto, String enderecoIp);
    List<UsuarioResponseDTO> listarUsuarios();
}
