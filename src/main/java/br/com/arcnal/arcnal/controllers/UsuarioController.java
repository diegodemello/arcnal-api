package br.com.arcnal.arcnal.controllers;

import br.com.arcnal.arcnal.dtos.UsuarioReqDTO;
import br.com.arcnal.arcnal.dtos.UsuarioRespDTO;
import br.com.arcnal.arcnal.services.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @PostMapping("/usuario")
    public ResponseEntity<Void> cadastrarUsuario(@Valid @RequestBody UsuarioReqDTO dto, String enderecoIp) {
        usuarioService.cadastrarUsuario(dto, enderecoIp);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioRespDTO>> listarUsuarios(){
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }
}
