package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.request.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.QuestaoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Questao;
import br.com.arcnal.arcnal.application.dto.response.ResolucaoQuestaoResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = ImagemMapper.class
)
public interface QuestaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Mapping(target = "materia", ignore = true)
    @Mapping(target = "banca", ignore = true)
    @Mapping(target = "assunto", ignore = true)
    @Mapping(target = "imagens", ignore = true)
    @Mapping(target = "alternativas.alternativaA", source = "dto.alternativaA")
    @Mapping(target = "alternativas.alternativaB", source = "dto.alternativaB")
    @Mapping(target = "alternativas.alternativaC", source = "dto.alternativaC")
    @Mapping(target = "alternativas.alternativaD", source = "dto.alternativaD")
    @Mapping(target = "alternativas.alternativaE", source = "dto.alternativaE")
    @Mapping(target = "alternativas.alternativaCorreta", source = "dto.alternativaCorreta")
    @Mapping(target = "correcao.textoCorrecao", source = "dto.textoCorrecao")
    @Mapping(target = "correcao.videoCorrecao", source = "dto.videoCorrecao")
    @Mapping(target = "metadados.ano", source = "dto.ano")
    @Mapping(target = "metadados.enunciado", source = "dto.enunciado")
    Questao toEntity(QuestaoRequestDTO dto);

    @Mapping(target = "banca", source = "banca.nome")
    @Mapping(target = "materia", source = "materia.nome")
    @Mapping(target = "assunto", source = "assunto.nome")
    @Mapping(target = "ano", source = "metadados.ano")
    @Mapping(target = "enunciado", source = "metadados.enunciado")
    @Mapping(target = "imagens", source = "imagens")
    QuestaoResponseDTO toResponse(Questao entity);

    @Mapping(target = "banca", source = "banca.nome")
    @Mapping(target = "materia", source = "materia.nome")
    @Mapping(target = "assunto", source = "assunto.nome")
    @Mapping(target = "ano", source = "metadados.ano")
    @Mapping(target = "enunciado", source = "metadados.enunciado")
    List<QuestaoResponseDTO> toResponses(List<Questao> questoes);

    @Mapping(target = "textoCorrecao", source = "correcao.textoCorrecao")
    @Mapping(target = "videoCorrecao", source = "correcao.videoCorrecao")
    ResolucaoQuestaoResponseDTO toResolucaoResponse(Questao entity);
}
