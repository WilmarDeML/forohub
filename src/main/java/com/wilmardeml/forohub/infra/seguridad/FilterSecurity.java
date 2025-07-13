package com.wilmardeml.forohub.infra.seguridad;

import com.wilmardeml.forohub.servicios.AutenticacionService;
import com.wilmardeml.forohub.servicios.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AutenticacionService autenticacionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var JWToken = recuperarToken(request);

        if (JWToken != null) {
            var username = tokenService.obtenerUsernameDelToken(JWToken);
            var usuarioBuscado = autenticacionService.loadUserByUsername(username);

            var autenticacion = new UsernamePasswordAuthenticationToken(
                    usuarioBuscado, null, usuarioBuscado.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationToken = request.getHeader("Authorization");
        if (authorizationToken != null)
            return authorizationToken.split(" ")[1];

        return null;
    }
}
