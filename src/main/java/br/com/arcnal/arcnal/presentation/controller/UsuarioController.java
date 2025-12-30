package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.application.dto.AutenticacaoRequestDTO;
import br.com.arcnal.arcnal.application.dto.LoginResponseDTO;
import br.com.arcnal.arcnal.application.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.application.dto.UsuarioResponseDTO;
import br.com.arcnal.arcnal.infra.security.TokenService;
import br.com.arcnal.arcnal.application.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(@RequestParam Integer pagina, Integer objetos){
        return ResponseEntity.ok().body(usuarioService.listarUsuarios(pagina, objetos));
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticacaoRequestDTO body){
        var emailSenha = new UsernamePasswordAuthenticationToken(body.email(), body.senha());
        var autenticacao = authenticationManager.authenticate(emailSenha);

        var token = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
