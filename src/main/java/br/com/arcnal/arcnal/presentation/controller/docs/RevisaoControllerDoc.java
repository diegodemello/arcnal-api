package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.DetalheRevisaoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "Revisão", description = "Responsável pela criação, listagem, resposta e visualização de resolução de questão.")
public interface RevisaoControllerDoc {
    @Operation(
            summary = "Criar revisão",
            description = "Cria uma nova revisão."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Revisão criada com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento da requisição."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse (responseCode = "403", description = "Usuário não autorizado para essa rota."),
            @ApiResponse (responseCode = "404", description = "Uma ou mais questões não foram encontradas.")

    })
    ResponseEntity<Void> criarRevisao(RevisaoRequestDTO dto, UserDetails userDetails);

    @Operation(
            summary = "Listar revisões",
            description = "Lista todas as revisões de um usuário."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Revisões listadas com sucesso."),
            @ApiResponse (responseCode = "404", description = "Nenhuma revisão encontrada para o usuário ou usuário não encontrado.")
    })
    ResponseEntity<List<RevisaoResponseDTO>> listarRevisoesPorUsuario(UUID idUsuario);

    @Operation(
            summary = "Listar revisão",
            description = "Lista todas as questões de uma revisão."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Revisão listada com sucesso.")
    })
    ResponseEntity<DetalheRevisaoResponseDTO> listarRevisao(Integer pagina, Integer objetos, UUID idRevisao);
}
