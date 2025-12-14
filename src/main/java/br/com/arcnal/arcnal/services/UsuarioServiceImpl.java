package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dtos.UsuarioReqDTO;
import br.com.arcnal.arcnal.dtos.UsuarioRespDTO;
import br.com.arcnal.arcnal.entities.Usuario;
import br.com.arcnal.arcnal.entities.enums.Cargo;
import br.com.arcnal.arcnal.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper usuarioMapper;

    @Override
    public void cadastrarUsuario(UsuarioReqDTO dto, String enderecoIp) {
        if(usuarioDAO.existsByEmail(dto.email())){
            throw new RuntimeException("Esse e-mail já está em uso, faça o login.");
        }

        Usuario usuario = usuarioMapper.requestToEntity(dto);
        usuario.setEnderecoIp(enderecoIp);
        usuario.setCargo(Cargo.USUARIO);
        usuarioDAO.save(usuario);
    }

    @Override
    public List<UsuarioRespDTO> listarUsuarios() {
        List<UsuarioRespDTO> usuarios = usuarioMapper.entitiesToDto(usuarioDAO.findAll());
        return usuarios;
    }
}
