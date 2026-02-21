package br.com.arcnal.arcnal.application.service.impl;

import br.com.arcnal.arcnal.application.service.ISenhaService;
import br.com.arcnal.arcnal.domain.entities.SenhaRecuperada;
import br.com.arcnal.arcnal.domain.entities.Usuario;
import br.com.arcnal.arcnal.domain.exception.EmailInvalidoException;
import br.com.arcnal.arcnal.domain.repositories.SenhaRecuperadaRepository;
import br.com.arcnal.arcnal.domain.repositories.UsuarioRepository;
import br.com.arcnal.arcnal.infra.util.EnvioDeEmail;
import com.auth0.jwt.exceptions.TokenExpiredException;
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

    @Override
    @Async
    @Transactional
    public void recuperarSenha(String email) {
        validarEmail(email);
        if(existeUsuarioComEsseEmail(email)) {
            String tokenGerado = gerarTokenRecuperacaoSenha();
            SenhaRecuperada senhaRecuperada = new SenhaRecuperada();
            senhaRecuperada.setUsuario(usuarioRepository.findByEmailEndereco(email).orElse(null));
            senhaRecuperada.setToken(tokenGerado);
            senhaRecuperada.setUtilizado(false);
            senhaRecuperada.setDataExpiracao(LocalDateTime.now().plusMinutes(30));
            senhaRecuperadaRepository.save(senhaRecuperada);

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

        String html = """
          <table style="margin:0; padding:0; background-color:#f4f4f4; font-family: Arial, Helvetica, sans-serif;" width="100%" cellpadding="0" cellspacing="0" border="0" bgcolor="#f4f4f4">
            <tr>
              <td align="center">
                <table width="600" cellpadding="0" cellspacing="0" border="0" bgcolor="#ffffff" style="margin: 20px auto;">
                  <tr>
                    <td align="center">
                      <img src="arcnal-email-header.jpg" alt="Arcnal" width="600" style="display:block; width:100%; max-width:600px;">
                    </td>
                  </tr>
                  <tr>
                    <td style="padding:40px 40px 20px 40px; color:#333333; font-size:16px; line-height:1.6;">
                      <p style="margin:0 0 20px 0;">Olá, <strong>{{nome}}</strong>.</p>
                      <p style="margin:0 0 20px 0;">
                        Recebemos uma solicitação para redefinir a senha da sua conta na <strong>Arcnal</strong>.
                      </p>
                      <p style="margin:0 0 30px 0;">
                        Para criar uma nova senha, clique no botão abaixo:
                      </p>
                      <table cellpadding="0" cellspacing="0" border="0" align="center" style="margin-bottom:30px;">
                        <tr>
                          <td align="center" bgcolor="#75A932" style="border-radius:6px;">
                            <a href="{{link_recuperacao}}"
                               target="_blank"
                               style="display:inline-block; padding:14px 28px; font-size:16px; color:#ffffff; text-decoration:none; font-weight:bold;">
                               Redefinir senha
                            </a>
                          </td>
                        </tr>
                      </table>
                      <p style="margin:0 0 15px 0; font-size:14px; color:#555555;">
                        Este link é pessoal e válido por <strong>30 minutos</strong>.
                      </p>
                      <p style="margin:0 0 20px 0; font-size:14px; color:#555555;">
                        Caso você não tenha solicitado essa alteração, desconsidere este e-mail. Nenhuma modificação será realizada sem a confirmação pelo link acima.
                      </p>
                      <p style="margin:30px 0 0 0;">
                        Atenciosamente,<br>
                        <strong>Equipe Arcnal</strong>
                      </p>
                    </td>
                  </tr>
                  <tr>
                    <td align="center">
                      <img src="arcnal-email-footer.jpg" alt="Arcnal Footer" width="600" style="display:block; width:100%; max-width:600px;">
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
          """;
        html = html.replace("{{link_recuperacao}}", link);
        html = html.replace("{{nome}}", usuario.getNome());
        

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
}