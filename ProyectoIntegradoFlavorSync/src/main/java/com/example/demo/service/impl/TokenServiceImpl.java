package com.example.demo.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PasswordResetToken;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.service.TokenService;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {
	
	// Tiempo de expiración en minutos (ej. 15 minutos)
	private static final int EXPIRATION_TIME = 15;

	@Autowired
	@Qualifier("passwordResetTokenRepository")
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public String generateToken(String email) {
		LocalDateTime now = LocalDateTime.now().plusHours(2);
		String input = email + now.toString();

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {

		}
		return input;
	}

	@Override
	public boolean isTokenValid(String token) {
		PasswordResetToken storedToken = passwordResetTokenRepository.findByToken(token);
		return storedToken != null && LocalDateTime.now().isAfter(storedToken.getExpirationTime());
	}

	@Override
	public void saveTokenToDatabase(String token, String email) {
		LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, email, expirationTime);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public String getEmailFromToken(String token) {
		PasswordResetToken storedToken = passwordResetTokenRepository.findByToken(token);
		if (storedToken != null) {
			return storedToken.getUserEmail(); 
		}
		return null; 
	}

	@Override
	public void removeExistingTokensForEmail(String email) {
		passwordResetTokenRepository.deleteByUserEmail(email); 
	}

	@Override
	public void cleanupExpiredTokens() {
		passwordResetTokenRepository.deleteExpiredTokens(LocalDateTime.now()); 
	}
}
