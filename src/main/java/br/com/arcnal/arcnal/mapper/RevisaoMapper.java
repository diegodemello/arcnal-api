package br.com.arcnal.arcnal.mapper;

import br.com.arcnal.arcnal.dto.RevisaoRequestDTO;
import br.com.arcnal.arcnal.dto.RevisaoResponseDTO;
import br.com.arcnal.arcnal.domain.Revisao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {QuestaoMapper.class})
public interface RevisaoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "questoes", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Revisao toEntity(RevisaoRequestDTO dto);

    @Mapping(target = "idUsuario", source = "usuario.id")
    List<RevisaoResponseDTO> toResponse(List<Revisao> revisoes);
}
