package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.IUsuarioService;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import br.com.arcnal.arcnal.application.dto.request.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.UsuarioResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.enums.Cargo;
import br.com.arcnal.arcnal.domain.exception.EmailEmUsoException;
import br.com.arcnal.arcnal.application.mapper.UsuarioMapper;
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

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public void cadastrarUsuario(UsuarioRequestDTO dto) {
        validarEmailUnico(dto.email());
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setCargo(Cargo.USUARIO);
        String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.senha());
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);
        log.info("Usuário criado com ID = " + usuario.getId() + " e email = " + usuario.getEmail());
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios(Integer pagina, Integer objetos) {
        return usuarioMapper.toResponse(usuarioRepository.findAll(PageRequest.of(pagina, objetos)).getContent());
    }

    private void validarEmailUnico(String email){
        if(usuarioRepository.existsByEmail(email)){
            log.warn("Usuário tentando criar conta com e-mail repetido! E-mail = " + email);
            throw new EmailEmUsoException("O email " + email + " já está em uso.");
        }
    }
}
