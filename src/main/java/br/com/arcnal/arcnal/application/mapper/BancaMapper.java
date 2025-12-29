package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Banca;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BancaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome")
    Banca toEntity(BancaRequestDTO dto);

    default Nome stringToNome(String nome) {
        return nome != null ? new Nome(nome) : null;
    }

    default String nomeToString(Nome nome) {
        return nome != null ? nome.getNome() : null;
    }
}