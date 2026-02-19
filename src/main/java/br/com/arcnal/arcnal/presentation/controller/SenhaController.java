package br.com.arcnal.arcnal.presentation.controller;

import br.com.arcnal.arcnal.application.dto.request.RecuperarSenhaRequestDTO;
import br.com.arcnal.arcnal.application.dto.request.RedefinirSenhaRequestDTO;
import br.com.arcnal.arcnal.application.service.ISenhaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/senha")
@AllArgsConstructor
public class SenhaController {

    private final ISenhaService senhaService;

    @PostMapping("/recuperar")
    public void recuperarSenha(@RequestBody RecuperarSenhaRequestDTO requestDTO){
        senhaService.recuperarSenha(requestDTO.email());
    }

    @PostMapping("/redefinir")
    public void redefinirSenha(@RequestBody RedefinirSenhaRequestDTO requestDTO) {
        senhaService.redefinirSenha(requestDTO.token(), requestDTO.senha());
    }
}
