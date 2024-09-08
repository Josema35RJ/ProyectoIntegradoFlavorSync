package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.converter.CookConverter;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CookService;

@Controller
public class AprendizController {

	private static final String PANEL_VIEW = "/auth/cookAprendiz/aprendizPanel";
	private static final String PANELPERFIL_VIEW = "/auth/cookAprendiz/aprendizPerfil";
	private static final String FORMRECIPE_VIEW = "/auth/cookAprendiz/formRecipe";

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;

	@Autowired
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@GetMapping("/auth/cookAprendiz/aprendizPanel")
	public String PanelAprendiz() {
		return PANEL_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cookAprendiz/perfilAprendiz")
	public String PerfilAprendiz(Model model, Authentication authentication) {
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    // Ahora puedes obtener la información del usuario logueado desde userDetails
	    model.addAttribute("cookPerfil", cookService.findById(userDetails.getId()));
	    return PANELPERFIL_VIEW;
	}
	
	@GetMapping("/auth/cookAprendiz/formRecipe")
	public String FormRecipe(Model model, Authentication authentication) {
	    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
	    // Ahora puedes obtener la información del usuario logueado desde userDetails
	    model.addAttribute("cookPerfil", cookService.findById(userDetails.getId()));
	    return FORMRECIPE_VIEW;
	}

}