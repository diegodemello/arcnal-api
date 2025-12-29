package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.UsuarioResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enderecoIp", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "cargo", ignore = true)
    @Mapping(target = "banido", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Usuario toEntity(UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> toResponse(List<Usuario> usuarios);
}
