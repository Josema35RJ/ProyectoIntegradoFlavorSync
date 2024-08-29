package com.example.demo.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

	private int id;

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
			int i) {
		super(username, password, authorities);
		this.id = i;
	}

	public int getId() {
		return id;
	}
}
