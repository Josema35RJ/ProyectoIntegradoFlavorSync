package com.example.demo.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Error de inicio de sesión. Por favor, verifica tus credenciales.";
        // Personaliza el mensaje según el tipo de excepción lanzada
        if (exception.getMessage()=="El cocinero no está activado") {
            errorMessage = "Tu cuenta no está activada. Revisa tu correo para activarla.";
        } else if (exception instanceof LockedException) {
            errorMessage = "Tu cuenta ha sido bloqueada. Contacta al administrador.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Credenciales incorrectas. Inténtalo de nuevo.";
        }

        // Guarda el mensaje de error en la sesión y redirige a la página de login
        request.getSession().setAttribute("error", errorMessage);
        response.sendRedirect("/login");
    }
}
