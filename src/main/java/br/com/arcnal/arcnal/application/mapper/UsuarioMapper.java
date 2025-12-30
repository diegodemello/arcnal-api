package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.UsuarioResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.valueobjects.Email;
import br.com.arcnal.arcnal.domain.valueobjects.NomeUsuario;
import br.com.arcnal.arcnal.domain.valueobjects.Senha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "cargo", ignore = true)
    @Mapping(target = "banido", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    Usuario toEntity(UsuarioRequestDTO dto);

    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "senha", source = "senha")
    List<UsuarioResponseDTO> toResponse(List<Usuario> usuarios);

    default NomeUsuario stringToNome(String nome) {
        return nome != null ? new NomeUsuario(nome) : null;
    }

    default String nomeToString(NomeUsuario nomeUsuario) {
        return nomeUsuario != null ? nomeUsuario.getNome() : null;
    }


    default Email stringToEmail(String email) {
        return email != null ? new Email(email) : null;
    }

    default String emailToString(Email email) {
        return email != null ? email.getEndereco() : null;
    }


    default Senha stringToSenha(String senha) {
        return senha != null ? new Senha(senha) : null;
    }

    default String senhaToString(Senha senha) {
        return senha != null ? senha.getSenha() : null;
    }
}
