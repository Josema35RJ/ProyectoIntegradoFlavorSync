package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String token;

	@Column(nullable = false)
	private String userEmail;

	@Column(nullable = false)
	private LocalDateTime expirationTime;

	// Getters y Setters

	public PasswordResetToken() {
	}

	public PasswordResetToken(String token, String userEmail, LocalDateTime expirationTime) {
		this.token = token;
		this.userEmail = userEmail;
		this.expirationTime = expirationTime;
	}

	// Getters and Setters
}
