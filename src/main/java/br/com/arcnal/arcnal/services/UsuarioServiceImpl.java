package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dtos.UsuarioRequestDTO;
import br.com.arcnal.arcnal.dtos.UsuarioResponseDTO;
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
    public void cadastrarUsuario(UsuarioRequestDTO dto, String enderecoIp) {
        validarEmailUnico(dto.email());

        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setEnderecoIp(enderecoIp);
        usuario.setCargo(Cargo.USUARIO);
        usuarioDAO.save(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioMapper.toResponse(usuarioDAO.findAll());
    }

    private void validarEmailUnico(String email){
        if(usuarioDAO.existsByEmail(email)){
            throw new RuntimeException("Esse e-mail já está em uso.");
        }
    }
}
