package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.impl.EmailServiceImpl;

@Controller
public class AuthController {

    @Autowired
    private EmailServiceImpl emailService;

    @PostMapping("/auth/recover-password")
    public String recoverPassword(@RequestParam("recoverEmail") String email, RedirectAttributes redirectAttributes) {
        // Aquí debes verificar si el correo está registrado en tu base de datos
        if (isEmailRegistered(email)) {
            // Generar un enlace de recuperación (puedes usar un token único para mayor seguridad)
            String recoveryLink = "http://tu_dominio.com/reset-password?token=TOKEN_UNICO";

            // Llamar al servicio para enviar el correo
            emailService.sendRecoveryEmail(email, recoveryLink);
            redirectAttributes.addFlashAttribute("success", "Se ha enviado un enlace de recuperación a tu correo.");
        } else {
            redirectAttributes.addFlashAttribute("error", "El correo electrónico no está registrado.");
        }
        return "redirect:/login"; // Redirigir de nuevo a la página de inicio de sesión
    }

    private boolean isEmailRegistered(String email) {
        // Lógica para verificar si el correo electrónico está registrado en tu base de datos
        return true; // Cambia esto por la lógica real
    }
}
