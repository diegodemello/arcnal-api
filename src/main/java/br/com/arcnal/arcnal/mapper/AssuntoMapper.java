package br.com.arcnal.arcnal.mapper;

import br.com.arcnal.arcnal.dtos.AssuntoRequestDTO;
import br.com.arcnal.arcnal.dtos.AssuntoResponseDTO;
import br.com.arcnal.arcnal.entities.Assunto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AssuntoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "materia", ignore = true)
    Assunto toEntity(AssuntoRequestDTO dto);

    AssuntoResponseDTO toResponse(Assunto entity);
}
