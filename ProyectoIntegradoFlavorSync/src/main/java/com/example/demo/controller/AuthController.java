package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.cook;
import com.example.demo.model.cookModel;
import com.example.demo.service.CookService;
import com.example.demo.service.impl.EmailServiceImpl;
import com.example.demo.service.impl.TokenServiceImpl;

import jakarta.mail.MessagingException;

@Controller
public class AuthController {

	private static final String LOGIN_VIEW = "/auth/login";

	@Autowired
	@Qualifier("emailService")
	private EmailServiceImpl emailService;

	@Autowired
	@Qualifier("tokenService")
	private TokenServiceImpl tokenService;

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;

	@PostMapping("/auth/recover-password")
	public String recoverPassword(@RequestParam("recoverEmail") String email, RedirectAttributes redirectAttributes) {
		if (isEmailRegistered(email)) {
			String token = tokenService.generateToken(email); // Generar token único para el enlace
			tokenService.saveTokenToDatabase(token, email);
			String recoveryLink = "http://localhost:8080/auth/resetPassword/" + token;

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

	@GetMapping("/auth/verify-email/{token}")
	public String verifyEmail(@PathVariable("token") String token, RedirectAttributes flash) {
		String email = tokenService.getEmailFromToken(token); // Obtén el email a partir del token
		if (email != null && tokenService.isTokenValid(token)) {
			// Actualiza el estado de verificación del usuario en la base de datos
			cookService.verifyUserEmail(email);
			flash.addFlashAttribute("success", "¡Tu correo ha sido verificado exitosamente!");
		} else {
			flash.addFlashAttribute("error", "El enlace de verificación es inválido o ha expirado.");
		}
		return "redirect:" + LOGIN_VIEW;
	}

	@Scheduled(cron = "0 */10 * * * *") // Ejecutar cada hora
	public void cleanUpUnverifiedUsers() {
		// Buscar tokens expirados
		LocalDateTime now = LocalDateTime.now();
		List<cookModel> unverifiedCooks = cookService.findUnverifiedCooks(now);

		// Eliminar cada usuario no verificado cuyo token ha expirado
		for (cookModel cook : unverifiedCooks) {
			cookService.deletedCook(cook.getId());
			tokenService.deleteByEmail(cook.getUsername()); // Eliminar el token asociado
			System.out.println("Usuario no verificado eliminado: " + cook.getUsername());
		}
	}

}
