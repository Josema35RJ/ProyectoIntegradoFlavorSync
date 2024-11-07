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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public HttpFirewall allowSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true); // Permitir puntos y comas
        return firewall;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/imgs/*", "/auth/**", "/webjars/*", "/css/*", "/files/", "/js/*").permitAll()
                .requestMatchers("/auth/cook/*").hasAnyAuthority(
                    "ROL_COOKCHEF", "ROL_COOKPROFESSIONAL", "ROL_COOKAPRENDIZ", "ROL_COOKAMATEUR")
                .anyRequest().authenticated())
            .formLogin((form) -> form
                .loginPage("/auth/login")
                .failureHandler(new CustomAuthenticationFailureHandler()) // Redirección en caso de fallo
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {
                        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                        if (roles.contains("ROL_COOKAPRENDIZ")) {
                            response.sendRedirect("cook/cookPanel");
                        } else {
                            response.sendRedirect("/"); // Redirige al home o donde prefieras en caso de otros roles
                        }
                    }
                }).permitAll())
            .logout((logout) -> logout
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login?logout=true") // Redirección con logout=true en caso de cierre de sesión
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"))
            .headers(headers -> headers.cacheControl());

        return http.build();
    }
}