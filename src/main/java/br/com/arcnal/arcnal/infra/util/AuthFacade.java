package br.com.arcnal.arcnal.infra.util;

import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.exception.UsuarioNaoEncontradoException;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final UsuarioRepository repository;

    public Usuario getUsuarioAutenticado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new UsuarioNaoEncontradoException("Usuário não autenticado");
        }

        String email = authentication.getName();
        return repository.findByEmailEndereco(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado com o email: " + email));
    }
}
