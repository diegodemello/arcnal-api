package br.com.arcnal.arcnal.application.mapper;

import br.com.arcnal.arcnal.application.dto.request.ImagemRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.ImagemResponseDTO;
import br.com.arcnal.arcnal.domain.entities.Imagem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImagemMapper {
    @Mapping(target = "arquivoInfo.nomeArquivo", source = "nomeArquivo")
    @Mapping(target = "arquivoInfo.caminho", source = "caminho")
    @Mapping(target = "arquivoInfo.tamanho", source = "tamanho")
    @Mapping(target = "questao", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    Imagem toEntity(ImagemRequestDTO dto);

    @Mapping(target = "caminhoImagem", source = "caminho")
    ImagemResponseDTO toResponseDTO(Imagem imagem);
}