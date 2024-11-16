package com.example.demo.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";

    @Value("${jwt.expirationTimeInMillis}")
    private long expirationTimeInMillis; // 1 hora

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            // Solo filtrar las rutas que requieren JWT, como "/api/" y "/cookapp/"
            if (shouldFilter(request)) {
                if (existeJWTToken(request, response)) {
                    Claims claims = validateToken(request);
                    if (claims.get("authorities") != null) {
                        setUpSpringAuthentication(claims, request);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
            // Continúa con el filtro de la cadena
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    private void setUpSpringAuthentication(Claims claims, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                .map(role -> {
                    // Aplicar roles según las rutas
                    if ((role.equals("ROL_COOKCHEF") || role.equals("ROL_COOKPROFESSIONAL")
                            || role.equals("ROL_COOKAPRENDIZ") || role.equals("ROL_COOKAMATEUR"))
                            && !request.getRequestURI().startsWith("/api/cook/")) {
                        return null; // No autorizar si la ruta no empieza con "/api/cook/"
                    }
                    return new SimpleGrantedAuthority(role);
                })
                .filter(role -> role != null)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    public String generateToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTimeInMillis);
        return Jwts.builder().setSubject(username).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    // Método para determinar si una ruta debe ser filtrada por JWT
    private boolean shouldFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // Se aplica JWT solo a las rutas de "/api/" y "/cookapp/"
        return uri.startsWith("/api/") || uri.startsWith("/cookapp/");
    }
}
