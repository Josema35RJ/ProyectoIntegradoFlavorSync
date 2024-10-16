package com.example.demo.service;

public interface TokenService {

	String generateToken(String email);
	boolean isTokenValid(String token);
	void saveTokenToDatabase(String token, String email);
	 String getEmailFromToken(String token);
	 void removeExistingTokensForEmail(String email);
	 void cleanupExpiredTokens();
}
