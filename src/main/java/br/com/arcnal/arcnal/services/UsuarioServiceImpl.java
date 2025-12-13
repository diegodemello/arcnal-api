package br.com.arcnal.arcnal.services;

import br.com.arcnal.arcnal.dao.UsuarioDAO;
import br.com.arcnal.arcnal.dtos.UsuarioReqDTO;
import br.com.arcnal.arcnal.dtos.UsuarioRespDTO;
import br.com.arcnal.arcnal.entities.Usuario;
import br.com.arcnal.arcnal.entities.enums.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    UsuarioDAO dao;

    @Override
    public void cadastrarUsuario(UsuarioReqDTO dto, String enderecoIp) {
        if(dao.existsByEmail(dto.email())){
            throw new RuntimeException("Esse e-mail já está em uso, faça o login.");
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(dto.senha())
                .enderecoIp(enderecoIp)
                .cargo(Cargo.USUARIO)
                .banido(false)
                .build();
        dao.save(usuario);
    }

    @Override
    public List<UsuarioRespDTO> listarUsuarios() {
        List<UsuarioRespDTO> dto = dao.findAll().stream()
                .map(usuario -> UsuarioRespDTO.builder()
                        .id(usuario.getId())
                        .nome(usuario.getNome())
                        .email(usuario.getEmail())
                        .banido(usuario.isBanido())
                        .cargo(usuario.getCargo())
                        .build()
                ).toList();
        return dto;
    }
}
