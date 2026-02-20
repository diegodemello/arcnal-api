package br.com.arcnal.arcnal.application.service;

public interface ISenhaService {
        void recuperarSenha(String email);
        void redefinirSenha(String token, String novaSenha);
}
