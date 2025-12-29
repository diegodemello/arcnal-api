package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Materia;
import br.com.arcnal.arcnal.domain.valueobjects.Nome;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MateriaMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", source = "nome")
    Materia toEntity(MateriaRequestDTO dto);

    default Nome stringToNome(String nome) {
        return nome != null ? new Nome(nome) : null;
    }

    default String nomeToString(Nome nome) {
        return nome != null ? nome.getNome() : null;
    }
}
