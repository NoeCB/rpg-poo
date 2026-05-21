package com.dbd.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
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
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = null;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        } else if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("jwt_token".equals(cookie.getName())) {
                    jwt = cookie.getValue();
                    break;
                }
            }
        }

        if (jwt != null && !jwt.isBlank()) {
            if (jwt.isBlank()) { // Mantener control de blanco
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    
                    if (username == null || username.isBlank()) {
                        System.err.println("Token proporcionado no contiene identidad válida.");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token Payload");
                        return;
                    }

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
                    
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException exc) {
                    System.err.println("Fallo de verificación de token JWT: " + exc.getMessage());
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                    return;
                } catch (Exception e) {
                    System.err.println("Excepción inesperada procesando token JWT: " + e.getMessage());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error procesando autenticación");
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
