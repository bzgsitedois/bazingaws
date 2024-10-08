package com.bazinga.config.security;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.bazinga.entity.Jogador;
import com.bazinga.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Value;
import com.auth0.jwt.JWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final JogadorRepository jogadorRepository;

    public TokenService(JogadorRepository usuarioRepository) {
        this.jogadorRepository = usuarioRepository;
    }

    public String generateToken(UserDetails usuario) {

        Jogador usuarioLogado = jogadorRepository.findByEmail(usuario.getUsername()).orElseThrow(RuntimeException::new);

        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("patrimonio-application")
                .withSubject(usuario.getUsername())
                .withClaim("id", usuarioLogado.getId())
                .withClaim("perfil", usuarioLogado.getPerfil().name())
                .withExpiresAt(generateExpiration())
                .sign(algorithm);
    }

    public TokenInfo validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("bazinga-application")
                .build()
                .verify(token);


        String subject = decodedJWT.getSubject();


        Long id = decodedJWT.getClaim("id").asLong();


        return new TokenInfo(id, subject);
    }

    private Instant generateExpiration() {
        return LocalDateTime.now().plusHours(240).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateEmailConfirmationToken(String emailAntigo, String emailNovo) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("bazinga-application")
                .withSubject(emailAntigo)
                .withClaim("emailNovo", emailNovo)
                .withExpiresAt(generateExpiration())
                .sign(algorithm);
    }

    public String validateEmailConfirmationToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("bazinga-application")
                .build()
                .verify(token);

        return decodedJWT.getClaim("emailNovo").asString();
    }

    public String getEmailAntigoFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer("bazinga-application")
                .build()
                .verify(token);

        return decodedJWT.getSubject();
    }
}
