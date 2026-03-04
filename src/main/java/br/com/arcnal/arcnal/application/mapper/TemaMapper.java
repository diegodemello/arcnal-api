package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.request.TemaRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.TemaResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Tema;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TemaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "text", source = "titulo")
    @Mapping(target = "textoDeApoio", source = "textoDeApoio")
    Tema toEntity(TemaRequestDTO request);
    TemaResponseDTO toResponseDTO(Tema tema);
    List<TemaResponseDTO> toResponsesDTOs(List<Tema> tema);

    default Nome stringToNome(String nome) {
        return nome != null ? new Nome(nome) : null;
    }

    default String nomeToString(Nome nome) {
        return nome != null ? nome.getNome() : null;
    }
}
