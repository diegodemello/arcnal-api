package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.AssuntoRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntoResponseDTO;
import br.com.arcnal.arcnal.application.dto.response.AssuntosMateriaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Assuntos", description = "Responsável pela criação de assuntos e listagem de assuntos por matéria.")
public interface AssuntoControllerDoc {

    @Operation(
            summary = "Criar assunto",
            description = "Cria um novo assunto para uma determinada matéria."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Assunto criado com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento do nome (numero minimo de caracteres ou uso de caracteres especiais)."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse (responseCode = "403", description = "Usuário não autorizado para essa rota."),
            @ApiResponse (responseCode = "404", description = "ID da matéria não existe.")
    })
    ResponseEntity<AssuntoResponseDTO> criarAssunto(AssuntoRequestDTO dto);

    @Operation(
            summary = "Listar assunto",
            description = "Lista todos assuntos de determinada matéria."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Assuntos listados com sucesso."),
            @ApiResponse (responseCode = "404", description = "ID da matéria não existe.")
    })
    ResponseEntity<AssuntosMateriaResponseDTO> listarAssuntosPorMateria(Integer idMateria);
}
