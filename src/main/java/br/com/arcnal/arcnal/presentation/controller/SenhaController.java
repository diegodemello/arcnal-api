package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.RecuperarSenhaRequestDTO;
import br.com.arcnal.arcnal.application.dto.request.RedefinirSenhaRequestDTO;
import br.com.arcnal.arcnal.application.dto.response.RecuperarSenhaResponseDTO;
import br.com.arcnal.arcnal.application.service.ISenhaService;
import br.com.arcnal.arcnal.domain.exception.NumeroMaximoAtingidoException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/senha")
@AllArgsConstructor
public class SenhaController {

    private final ISenhaService senhaService;

    @PostMapping("/recuperar")
    public ResponseEntity<RecuperarSenhaResponseDTO> recuperarSenha(@RequestBody RecuperarSenhaRequestDTO requestDTO){
        RecuperarSenhaResponseDTO response = senhaService.recuperarSenha(requestDTO.email());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/redefinir")
    public void redefinirSenha(@RequestBody RedefinirSenhaRequestDTO requestDTO) {
        senhaService.redefinirSenha(requestDTO.token(), requestDTO.senha());
    }
}
