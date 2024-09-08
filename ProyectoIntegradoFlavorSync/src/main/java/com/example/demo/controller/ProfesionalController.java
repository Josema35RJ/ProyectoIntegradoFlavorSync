package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.converter.CookConverter;
import com.example.demo.service.CookService;

@Controller
public class ProfesionalController {

	private static final String PANEL_VIEW = "/auth/cookProfesional/profesionalPanel";

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;

	@Autowired
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@GetMapping("/auth/cookProfesional/profesionalPanel")
	public String PanelProfesional() {
		return PANEL_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	

}