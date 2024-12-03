package com.example.demo.security;

import java.io.IOException;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Permitir puntos y comas en las URLs
    @Bean
    public HttpFirewall allowSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    // Configuración del AuthenticationManager para manejo de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración de seguridad para la API (protección mediante JWT)
    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**") // Aplica solo a rutas que comienzan con "/api/"
            .csrf().disable() // Deshabilita CSRF ya que se usa JWT
            .authorizeRequests()
                .requestMatchers("/api/login", "/api/register").permitAll() // Rutas públicas para login y registro
                .requestMatchers("/api/auth/cookapp/**").authenticated() // Rutas de la API protegidas por JWT
            .and()
            .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // Filtro JWT

        return http.build();
    }

    // Configuración de seguridad para la aplicación web (cookweb) - NO usa JWT
    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Deshabilita CSRF (puede ser necesario si usas cookies de sesión)
            .authorizeRequests()
                .requestMatchers("/", "/imgs/**", "/webjars/*", "/css/*", "/files/", "/js/*", "/favicon.ico", "/login", "/register", "/recover-password", "/reset-password/**", "/resetPassword/**", "/verify-email/**").permitAll() // Rutas públicas
                .requestMatchers("/auth/cookweb/**") // Rutas protegidas por roles
                .hasAnyAuthority("ROL_COOKCHEF", "ROL_COOKPROFESSIONAL", "ROL_COOKAPRENDIZ", "ROL_COOKAMATEUR") // Requiere un rol específico
                .anyRequest().authenticated() // Otras rutas requieren autenticación
            .and()
            .formLogin()
                .loginPage("/login") // Página de login
                .failureHandler(new CustomAuthenticationFailureHandler()) // Manejador de fallos en login
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                        if (roles.contains("ROL_COOKAMATEUR") || roles.contains("ROL_COOKAPRENDIZ")
                                || roles.contains("ROL_COOKPROFESIONAL") || roles.contains("ROL_COOKCHEF")) {
                            response.sendRedirect("/auth/cookweb/cookPanel"); // Redirigir según rol
                        } else {
                            response.sendRedirect("/"); // Redirigir a la página principal
                        }
                    }
                })
                .permitAll() // Permitir acceso a la página de login
            .and()
            .logout()
                .permitAll() // Permitir logout
                .logoutUrl("/logout") // URL para logout
                .logoutSuccessUrl("/login?logout=true") // Redirigir tras logout
                .invalidateHttpSession(true) // Invalidar sesión al salir
                .deleteCookies("JSESSIONID") // Eliminar cookies de sesión
            .and()
            .headers()
                .cacheControl(); // Control de caché para mayor seguridad

        return http.build();
    }

    // Filtro JWT
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }
}
