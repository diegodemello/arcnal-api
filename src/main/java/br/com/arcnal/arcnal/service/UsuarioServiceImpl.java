package br.com.arcnal.arcnal.service;

import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.dto.UsuarioResponseDTO;
import br.com.arcnal.arcnal.domain.Usuario;
import br.com.arcnal.arcnal.domain.enums.Cargo;
import br.com.arcnal.arcnal.exception.EmailEmUsoException;
import br.com.arcnal.arcnal.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
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
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.senha());
        usuario.setSenha(senhaCriptografada);
        usuarioDAO.save(usuario);
        log.info("Usuário criado com ID = " + usuario.getId() + " e email = " + usuario.getEmail());
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios(Integer pagina, Integer objetos) {
        return usuarioMapper.toResponse(usuarioDAO.findAll(PageRequest.of(pagina, objetos)).getContent());
    }

    private void validarEmailUnico(String email){
        if(usuarioDAO.existsByEmail(email)){
            log.warn("Usuário tentando criar conta com e-mail repetido! E-mail = " + email);
            throw new EmailEmUsoException("O email " + email + " já está em uso.");
        }
    }
}
