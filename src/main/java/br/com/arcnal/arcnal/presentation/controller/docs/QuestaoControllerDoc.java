package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.QuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.request.RespostaQuestaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.QuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.ResolucaoQuestaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RespostaQuestaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Questão", description = "Responsável pela criação, listagem, resposta e visualização de resolução de questão.")
public interface QuestaoControllerDoc {
    @Operation(
            summary = "Criar questão",
            description = "Cria uma nova questão."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Questão criada com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento da questão."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse (responseCode = "403", description = "Usuário não autorizado para essa rota."),
    })
    ResponseEntity<QuestaoResponseDTO> adicionarQuestao(QuestaoRequestDTO dto);

    @Operation(
            summary = "Listar questões",
            description = "Lista todas as questões paginadas."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Materias listadas com sucesso."),
    })
    ResponseEntity<List<QuestaoResponseDTO>> listarQuestoes(Integer pagina, Integer objetos);

    @Operation(
            summary = "Listar questões filtradas",
            description = "Lista todas as questões paginadas que sigam de filtros."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Questões listadas com sucesso."),
    })
    ResponseEntity<List<QuestaoResponseDTO>> listarQuestoesPorBancaAnoMateriaAssunto(Integer pagina, Integer objetos,
                                                                                     Integer idBanca, Integer ano,
                                                                                     Integer idMateria, Integer idAssunto);

    @Operation(
            summary = "Responder Questão",
            description = "Responde uma questão."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Questão respondida com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento da requisição."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
    })
    ResponseEntity<RespostaQuestaoResponseDTO> responderQuestao(Integer id, RespostaQuestaoRequestDTO request);

    @Operation(
            summary = "Resolução da Questão",
            description = "Mostra a resolução de uma questão."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Resolução retornada com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento da requisição."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
    })
    ResponseEntity<ResolucaoQuestaoResponseDTO> obterResolucaoQuestao(Integer id);
}
