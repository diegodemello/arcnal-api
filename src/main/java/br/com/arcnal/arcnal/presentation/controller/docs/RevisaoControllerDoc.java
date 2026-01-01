package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.RevisaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.RevisaoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

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
    })
    ResponseEntity<Void> criarRevisao(RevisaoRequestDTO dto, UserDetails userDetails);

    @Operation(
            summary = "Listar revisões",
            description = "Lista todas as revisões de um usuário."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Revisões listadas com sucesso."),
    })
    ResponseEntity<List<RevisaoResponseDTO>> listarRevisoesPorUsuario(UUID idUsuario);
}
