package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.MateriaRequestDTO;
import br.com.arcnal.arcnal.domain.entities.Materia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Materia", description = "Responsável pela criação de matérias.")
public interface MateriaControllerDoc {
    @Operation(
            summary = "Criar matéria",
            description = "Cria uma nova matéria."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Matéria criada com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento do nome (numero minimo de caracteres ou uso de caracteres especiais)."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse (responseCode = "403", description = "Usuário não autorizado para essa rota."),
            @ApiResponse (responseCode = "409", description = "Matéria com o mesmo nome já cadastrada.")
    })
    ResponseEntity<Materia> criarMateria(MateriaRequestDTO dto);

    @Operation(
            summary = "Listar materias",
            description = "Lista todas as materias."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Materias listadas com sucesso."),
    })
    ResponseEntity<List<Materia>> listarMaterias();
}
