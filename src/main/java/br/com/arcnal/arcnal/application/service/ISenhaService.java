package br.com.arcnal.arcnal.application.service;

import br.com.arcnal.arcnal.application.dto.response.RecuperarSenhaResponseDTO;

public interface ISenhaService {
        RecuperarSenhaResponseDTO recuperarSenha(String email);
        void redefinirSenha(String token, String novaSenha);
}
