package br.com.arcnal.arcnal.security;

import br.com.arcnal.arcnal.domain.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("arcnal-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException ex){
            throw new RuntimeException("Erro ao gerar token JWT", ex);
        }
    }

    public String validarToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("arcnal-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch(JWTVerificationException ex){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
