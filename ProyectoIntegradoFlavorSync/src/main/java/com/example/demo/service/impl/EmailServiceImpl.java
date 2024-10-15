package com.example.demo.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRecoveryEmail(String to, String recoveryLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("flavorsync7@gmail.com"); // Cambia a tu correo de envío
        message.setTo(to);
        message.setSubject("Recuperación de Contraseña");
        message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace: " + recoveryLink);

        mailSender.send(message);
    }
}
