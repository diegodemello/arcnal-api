package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.ISenhaService;
import br.com.arcnal.arcnal.domain.entities.SenhaRecuperada;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.exception.EmailInvalidoException;
import br.com.arcnal.arcnal.domain.repositories.SenhaRecuperadaRepository;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import br.com.arcnal.arcnal.infra.util.EnvioDeEmail;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.micrometer.core.instrument.Counter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class SenhaServiceImpl implements ISenhaService {

    private final UsuarioRepository usuarioRepository;
    private final SenhaRecuperadaRepository senhaRecuperadaRepository;
    private final PasswordEncoder passwordEncoder;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final EnvioDeEmail envioDeEmail;
    private final EmailTemplateServiceImpl emailTemplateService;
    private final Counter senhasRedefinidas;

    private static final int MAX_TOKENS_POR_USUARIO = 5;

    @Override
    @Async
    @Transactional
    public void recuperarSenha(String email) {
        validarEmail(email);
        if(existeUsuarioComEsseEmail(email)) {
            validarSePodeRedefinirSenha(email);
            String tokenGerado = gerarTokenRecuperacaoSenha();
            SenhaRecuperada senhaRecuperada = new SenhaRecuperada();
            senhaRecuperada.setUsuario(usuarioRepository.findByEmailEndereco(email).orElse(null));
            senhaRecuperada.setToken(tokenGerado);
            senhaRecuperada.setUtilizado(false);
            senhaRecuperada.setDataExpiracao(LocalDateTime.now().plusMinutes(30));
            senhaRecuperadaRepository.save(senhaRecuperada);
            senhasRedefinidas.increment();
            enviarEmailDeRecuperacao(tokenGerado, email);
        }
    }

    @Override
    public void redefinirSenha(String token, String novaSenha) {
        validarToken(token);
        Usuario usuario = obterUsuarioPorToken(token);
        usuario.setSenha(passwordEncoder.encode(novaSenha));

        SenhaRecuperada senhaRecuperada = senhaRecuperadaRepository.findByToken(token)
                .orElseThrow(() -> new TokenExpiredException("O token de recuperação de senha expirou ou já foi utilizado.", null));
        senhaRecuperada.setUtilizado(true);

        usuarioRepository.save(usuario);
        senhaRecuperadaRepository.save(senhaRecuperada);
    }

    private void enviarEmailDeRecuperacao(String token, String email) {
        if(email == null || email.isBlank()){
            throw new EmailInvalidoException("O email não pode ser vazio ou nulo.");
        }
        if(token == null || token.isBlank()){
            throw new IllegalArgumentException("O token não pode ser vazio ou nulo.");
        }

        Usuario usuario = obterUsuarioPorToken(token);

        String link = "https://arcnal.com.br/reset-password?token=" +
                URLEncoder.encode(token, StandardCharsets.UTF_8);

        String html = emailTemplateService.gerarEmailRecuperacao(usuario.getNome(), link);

        envioDeEmail.enviarEmail(email, "Arcnal - Recuperação de senha",
                html);
    }

    private void validarEmail(String email){
        if(email.isBlank() || email == null){
            throw new EmailInvalidoException("O email não pode ser vazio ou nulo.");
        }
        if(!email.matches("^[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+$")){
            throw new EmailInvalidoException("O email fornecido é inválido.");
        }
    }

    private boolean existeUsuarioComEsseEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    private String gerarTokenRecuperacaoSenha(){
        byte[] tokenBytes = new byte[32];
        SECURE_RANDOM.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private void validarToken(String token){
        if(token.isBlank() || token == null){
            throw new IllegalArgumentException("O token não pode ser vazio ou nulo.");
        }
        SenhaRecuperada senhaRecuperada = senhaRecuperadaRepository.findByToken(token)
                .orElseThrow(() -> new TokenExpiredException("O token de recuperação de senha expirou ou já foi utilizado.", null));
        if(senhaRecuperada.isUtilizado() || senhaRecuperada.getDataExpiracao().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("O token de recuperação de senha expirou ou já foi utilizado.", null);
        }
    }

    private Usuario obterUsuarioPorToken(String token){
        SenhaRecuperada senhaRecuperada = senhaRecuperadaRepository.findByToken(token)
                .orElseThrow(() -> new TokenExpiredException("O token de recuperação de senha expirou ou já foi utilizado.", null));
        return senhaRecuperada.getUsuario();
    }

    private Usuario obterUsuarioPorEmail(String email){
        return usuarioRepository.findByEmailEndereco(email)
                .orElseThrow(() -> new EmailInvalidoException("Não existe um usuário cadastrado com o email " + email));
    }

    private void validarSePodeRedefinirSenha(String email){
        Usuario usuario = obterUsuarioPorEmail(email);
        LocalDateTime agora = LocalDateTime.now();
        Long quantidade = senhaRecuperadaRepository.findAllByUsuarioId(usuario.getId())
                .stream()
                .filter(s -> s.getDataExpiracao().isAfter(agora) && s.getDataExpiracao().toLocalDate().equals(agora.toLocalDate()))
                .count();
        if(quantidade > MAX_TOKENS_POR_USUARIO){
            throw new EmailInvalidoException("Limite de tentativas de recuperação de senha atingido para o email " + email + ". Por favor, tente novamente amanhã.");
        }
    }
}