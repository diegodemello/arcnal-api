package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.BancaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Banca;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Banca Examinadora", description = "Responsável pela criação de banca examinadora.")
public interface BancaControllerDoc {

    @Operation(
            summary = "Criar banca",
            description = "Cria uma nova banca examinadora."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Banca criada com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento do nome (numero minimo de caracteres ou uso de caracteres especiais)."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse (responseCode = "403", description = "Usuário não autorizado para essa rota."),
    })
    ResponseEntity<Banca> adicionarBanca(BancaRequestDTO dto);

    @Operation(
            summary = "Listar bancas",
            description = "Lista todas as bancas examinadoras."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Bancas listadas com sucesso."),
    })
    ResponseEntity<List<Banca>> listarTodasBancas();
}
