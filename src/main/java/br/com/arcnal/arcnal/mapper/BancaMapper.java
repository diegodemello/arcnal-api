package br.com.arcnal.arcnal.mapper;

import br.com.arcnal.arcnal.dtos.BancaRequestDTO;
import br.com.arcnal.arcnal.entities.Banca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BancaMapper {
    @Mapping(target = "id", ignore = true)
    Banca toEntity(BancaRequestDTO dto);
}