package com.example.demo.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("emailService")
public class EmailServiceImpl {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	public void sendRecoveryEmail(String to, String recoveryLink, String subject) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		URI uri;
		String token = null;
		try {
			uri = new URI(recoveryLink);
			token = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1); // Extraer el token
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Crear un contexto de Thymeleaf para pasar los datos a la plantilla
		Context context = new Context();
		context.setVariable("recoveryLink", recoveryLink);
		// Procesar la plantilla HTML
		String htmlContent = templateEngine.process("/recoverPassword", context);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlContent, true); // Habilitar el contenido HTML

		mailSender.send(message);
	}

	public void sendVerificationEmail(String to, String verificationLink, String subject) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		URI uri;
		String token = null;
		try {
			uri = new URI(verificationLink);
			token = uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1); // Extrae el token
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		// Crear un contexto de Thymeleaf para pasar los datos a la plantilla
		Context context = new Context();
		context.setVariable("verificationLink", verificationLink); // Enlace de verificación
		context.setVariable("token", token); // Token extraído
		context.setVariable("username", to); // Puedes pasar más variables si lo necesitas

		// Procesar la plantilla HTML
		String htmlContent = templateEngine.process("/verifyEmail", context);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlContent, true); // Habilitar el contenido HTML

		mailSender.send(message);
	}
}
