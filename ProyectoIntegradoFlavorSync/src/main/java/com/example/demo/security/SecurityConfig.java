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
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/imgs/*", "/auth/**", "/webjars/*", "/css/*", "/files/", "/js/*").permitAll()
                .requestMatchers("/cook/*").hasAuthority("ROL_GYMCHEF")
                .requestMatchers("/cook/*").hasAuthority("ROL_COOKPROFESSIONAL")
                .requestMatchers("/cook/*").hasAuthority("ROL_COOKAPRENDIZ")
                .requestMatchers("/cook/*").hasAuthority("ROL_COOKAMATEUR")
                .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/auth/login")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                Authentication authentication) throws IOException, ServletException {
                                Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                               if (roles.contains("ROL_COOKAPRENDIZ")) {
                                    response.sendRedirect("cook/cookPanel");
                                }
                            }
                        }).permitAll())
                .logout((logout) -> logout
                        .permitAll()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .headers(headers -> headers.cacheControl());
        return http.build();
    }
}
