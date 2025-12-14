package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dtos.UsuarioReqDTO;
import br.com.arcnal.arcnal.dtos.UsuarioRespDTO;

import java.util.List;

public interface IUsuarioService {
    void cadastrarUsuario(UsuarioReqDTO dto, String enderecoIp);
    List<UsuarioRespDTO> listarUsuarios();
}
