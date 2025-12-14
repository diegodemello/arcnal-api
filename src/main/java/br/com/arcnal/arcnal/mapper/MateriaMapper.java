package br.com.arcnal.arcnal.mapper;

import br.com.arcnal.arcnal.dtos.MateriaReqDTO;
import br.com.arcnal.arcnal.entities.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MateriaMapper {
    @Mapping(target = "id", ignore = true)
    Materia materiaRequestToEntity(MateriaReqDTO dto);
}
