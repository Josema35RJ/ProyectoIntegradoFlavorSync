package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.CookService;
import com.example.demo.service.impl.EmailServiceImpl;
import com.example.demo.service.impl.TokenServiceImpl;

import jakarta.mail.MessagingException;

@Controller
public class AuthController {

	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private TokenServiceImpl tokenService;

	@Autowired
	private CookService cookService;

	@PostMapping("/auth/recover-password")
	public String recoverPassword(@RequestParam("recoverEmail") String email, RedirectAttributes redirectAttributes) {
		if (isEmailRegistered(email)) {
			String token = tokenService.generateToken(email); // Generar token único para el enlace
			tokenService.saveTokenToDatabase(token, email);
			String recoveryLink = "http://localhost:8080/auth/resetPassword/"+token;

			try {
				emailService.sendRecoveryEmail(email, recoveryLink, "Recuperación de contraseña");
				redirectAttributes.addFlashAttribute("success", "Se ha enviado un enlace de recuperación a tu correo.");
			} catch (MessagingException e) {
				redirectAttributes.addFlashAttribute("error", "Error al enviar el correo de recuperación.");
				e.printStackTrace();
			}
		} else {
			redirectAttributes.addFlashAttribute("error", "El correo electrónico no está registrado.");
		}
		return "redirect:/login";
	}

	private boolean isEmailRegistered(String email) {
		if (cookService.findByUsername(email) != null) {
			return true;
		}
		return false;
	}
	
	public String recoverPassword(String email) {
	    if (isEmailRegistered(email)) {
	        // Generar el token basado en el correo
	        String token = tokenService.generateToken(email);
	        
	        // Crear el enlace de recuperación
	        String recoveryLink = "http://flavorSync/reset-password?token=" + token;
	        
	        // Almacenar el token en la base de datos junto con la fecha de expiración
	        tokenService.saveTokenToDatabase(token, email);
	        
	        // Enviar el correo al usuario
	        try {
				emailService.sendRecoveryEmail(email, recoveryLink, "Recuperación de contraseña");
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        return "Se ha enviado un enlace de recuperación a tu correo.";
	    } else {
	        return "El correo electrónico no está registrado.";
	    }
	}


}
