package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.QuestaoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Questao;
import br.com.arcnal.arcnal.application.dto.ResolucaoQuestaoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface QuestaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "materia", ignore = true)
    @Mapping(target = "banca", ignore = true)
    @Mapping(target = "assunto", ignore = true)
    Questao toEntity(QuestaoRequestDTO dto);

    @Mapping(target = "banca", source = "banca.nome")
    @Mapping(target = "materia", source = "materia.nome")
    @Mapping(target = "assunto", source = "assunto.nome")
    QuestaoResponseDTO toResponse(Questao entity);
    List<QuestaoResponseDTO> toResponses(List<Questao> questoes);

    ResolucaoQuestaoResponseDTO toResolucaoResponse(Questao entity);
}
