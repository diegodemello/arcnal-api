package br.com.arcnal.arcnal.controller;

import br.com.arcnal.arcnal.dto.UsuarioRequestDTO;
import br.com.arcnal.arcnal.dto.UsuarioResponseDTO;
import br.com.arcnal.arcnal.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto, String enderecoIp) {
        usuarioService.cadastrarUsuario(dto, enderecoIp);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }
}
