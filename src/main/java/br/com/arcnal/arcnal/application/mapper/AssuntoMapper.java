package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.AssuntoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Assunto;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AssuntoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "materia", ignore = true)
    @Mapping(target = "nome", source = "nome")
    Assunto toEntity(AssuntoRequestDTO dto);

    @Mapping(target = "nome", source = "nome")
    AssuntoResponseDTO toResponse(Assunto entity);

    default Nome stringToNome(String nome) {
        return nome != null ? new Nome(nome) : null;
    }

    default String nomeToString(Nome nome) {
        return nome != null ? nome.getNome() : null;
    }
}
