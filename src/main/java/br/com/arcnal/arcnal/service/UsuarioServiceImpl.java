package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.dto.UsuarioResponseDTO;
import br.com.arcnal.arcnal.domain.Usuario;
import br.com.arcnal.arcnal.domain.enums.Cargo;
import br.com.arcnal.arcnal.exception.EmailEmUsoException;
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
            throw new EmailEmUsoException("O email " + email + " já está em uso.");
        }
    }
}
