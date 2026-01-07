package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.DetalheRevisaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.QuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Questao;
import br.com.arcnal.arcnal.domain.entities.Revisao;
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

    @Mapping(target = "numeroDeQuestoes", expression = "java(calculaONumeroDeQuestoes(revisao))")
    RevisaoResponseDTO toResponse(Revisao revisao);

    @Mapping(target = "numeroDeQuestoes", expression = "java(calculaONumeroDeQuestoesDaLista(revisoes))")
    List<RevisaoResponseDTO> toResponses(List<Revisao> revisoes);

    @Mapping(target = "questoes", source = "questoes")
    DetalheRevisaoResponseDTO toDetalheResponse(Revisao revisao);

    default Integer calculaONumeroDeQuestoes(Revisao revisao){
        if(revisao == null || revisao.getQuestoes() == null){
            throw new RuntimeException("Revisão está vazia ou nula.");
        }
        return revisao.getQuestoes().size();
    }

    default Integer calculaONumeroDeQuestoesDaLista(List<Revisao> revisoes){
        if(revisoes.isEmpty() || revisoes == null){
            throw new RuntimeException("Lista de revisões está vazia ou nula.");
        }
        return revisoes.stream()
                .mapToInt(revisao -> revisao.getQuestoes().size()).sum();
    }

    default DetalheRevisaoResponseDTO toDetalheResponse(Revisao revisao, List<Questao> questoesPaginadas) {
        List<QuestaoResponseDTO> questoesDTO = questoesPaginadas.stream()
                .map(questao -> QuestaoResponseDTO.builder()
                        .id(questao.getId())
                        .banca(questao.getBanca().getNome())
                        .materia(questao.getMateria().getNome())
                        .assunto(questao.getAssunto().getNome())
                        .nivel(questao.getNivel())
                        .ano(questao.getMetadados().getAno())
                        .enunciado(questao.getMetadados().getEnunciado())
                        .alternativaA(questao.getAlternativaA())
                        .alternativaB(questao.getAlternativaB())
                        .alternativaC(questao.getAlternativaC())
                        .alternativaD(questao.getAlternativaD())
                        .alternativaE(questao.getAlternativaE())
                        .build())
                .toList();

        return new DetalheRevisaoResponseDTO(
                revisao.getId(),
                revisao.getNome(),
                questoesDTO
        );
    }
}
