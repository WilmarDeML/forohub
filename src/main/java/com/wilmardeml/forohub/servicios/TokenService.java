package com.wilmardeml.forohub.servicios;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.jwt.secret}")
    private String SECRET;

    private final String ISSUER = "API forohub";

    public String generarToken(UserDetails usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getUsername())
                    .withClaim("perfiles", usuario.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).toList())
                    .withExpiresAt(getFechaDeExpiracion())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    public String obtenerUsernameDelToken(String JWToken) {
        try {
            var algoritmo = Algorithm.HMAC256(SECRET);
            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(JWToken)
                    .getSubject();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error validando el JWToken", e);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token JWT inválido o expirado!", e);
        }

    }

    private Instant getFechaDeExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
