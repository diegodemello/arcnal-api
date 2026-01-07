package br.com.arcnal.arcnal.application.dto.response;

import java.util.List;
import java.util.UUID;

public record DetalheRevisaoResponseDTO(
    UUID id,
    String nome,
    List<QuestaoResponseDTO> questoes
){

}