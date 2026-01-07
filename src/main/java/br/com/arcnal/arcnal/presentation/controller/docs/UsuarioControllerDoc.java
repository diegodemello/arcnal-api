package br.com.arcnal.arcnal.presentation.controller.docs;

import br.com.arcnal.arcnal.application.dto.request.AutenticacaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.request.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Usuário", description = "Responsável pela criação, login e listagem de usuários")
public interface UsuarioControllerDoc {
    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário."
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso."),
            @ApiResponse (responseCode = "400", description = "Erro no preenchimento da requisição."),
    })
    ResponseEntity cadastrarUsuario(UsuarioRequestDTO dto);

    @Operation(
            summary = "Listar usuários",
            description = "Listar todos os usuários paginados."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Usuários listados com sucesso."),
            @ApiResponse (responseCode = "401", description = "Usuário não autenticado."),
            @ApiResponse (responseCode = "403", description = "Usuário não autorizado para essa rota."),
    })
    ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(Integer pagina, Integer objetos);

    @Operation(
            summary = "Login",
            description = "Logar com uma conta já cadastrada."
    )
    @ApiResponses( value = {
            @ApiResponse (responseCode = "200", description = "Login efetuado com sucesso."),
            @ApiResponse (responseCode = "401", description = "Credenciais inválidas.")
    })
    ResponseEntity login(AutenticacaoRequestDTO body);
}
