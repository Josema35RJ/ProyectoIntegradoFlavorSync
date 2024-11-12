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
        firewall.setAllowSemicolon(true); // Permitir puntos y comas
        return firewall;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración para la API que usa JWT
    @Bean
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .requestMatchers("/api/login", "/api/register").permitAll()  // Permitir login y registro sin autenticación
                .requestMatchers("/api/auth/cook/**").authenticated()  // Requiere autenticación JWT
            .and()
            .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);  // Filtro JWT

        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .requestMatchers("/", "/imgs/**", "/webjars/*", "/css/*", "/files/", "/js/*").permitAll()  // Rutas públicas
                .requestMatchers("/auth/cook/**").hasAnyAuthority(  // Accesible solo con roles específicos
                    "ROL_COOKCHEF", "ROL_COOKPROFESSIONAL", "ROL_COOKAPRENDIZ", "ROL_COOKAMATEUR"
                )
                .anyRequest().authenticated()  // Cualquier otra ruta requiere autenticación
            .and()
            .formLogin(form -> form
                .loginPage("/login")  // Página de login personalizada
                .failureHandler(new CustomAuthenticationFailureHandler())  // Manejador de fallos de autenticación personalizado
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                            throws IOException, ServletException {
                        // Redirigir al panel después de un inicio de sesión exitoso
                        response.sendRedirect("/auth/cook/cookPanel");
                    }
                }).permitAll()
            )
            .logout(logout -> logout
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")  // Redirigir a login después de cerrar sesión
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")  // Eliminar cookies al cerrar sesión
            )
            .httpBasic()  // Autenticación básica para la API
            .and()
            .headers(headers -> headers.cacheControl());  // Deshabilitar caché

        return http.build();
    }

    // Filtro para JWT
    @Bean
    public JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }
}