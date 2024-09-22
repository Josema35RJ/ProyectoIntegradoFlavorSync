package com.example.demo.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;
import com.example.demo.service.IngredientService;
import com.example.demo.service.RecipeService;

@Controller
public class AprendizController {

	private static final String PANEL_VIEW = "/auth/cookAprendiz/aprendizPanel";
	private static final String PANELPERFIL_VIEW = "/auth/cookAprendiz/aprendizPerfil";
	private static final String FORMRECIPE_VIEW = "/auth/cookAprendiz/formRecipe";

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;
	
	@Autowired
	@Qualifier("recipeService")
	private RecipeService recipeService;

	@Autowired
	@Qualifier("ingredientService")
	private IngredientService ingredientService;
	
	@Autowired
	@Qualifier("culinaryTechniquesService")
	private CulinaryTechniquesService culinaryTechniquesService;

	@GetMapping("/auth/cookAprendiz/aprendizPanel")
	public String PanelAprendiz() {
		return PANEL_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cookAprendiz/perfilAprendiz")
	public String PerfilAprendiz(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());

		byte[] imageBytes = c.getImagePerfil();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);

		model.addAttribute("cookPerfil", c);
		model.addAttribute("passwordCook", c.getPassword());
		return PANELPERFIL_VIEW;
	}

	@PostMapping("auth/cookAprendiz/UpdatePerfil")
	public String UpdateCook(cookModel cNew) {
		cookModel cOrigin = cookService.findById(cNew.getId());
		if (!cOrigin.getNickName().equalsIgnoreCase(cNew.getNickName())) {
			cOrigin.setNickName(cNew.getNickName());
		} else if (!cOrigin.getFirstName().equalsIgnoreCase(cNew.getFirstName())) {
			cOrigin.setFirstName(cNew.getFirstName());
		} else if (!cOrigin.getLastName().equalsIgnoreCase(cNew.getLastName())) {
			cOrigin.setLastName(cNew.getLastName());
		} else if (!cOrigin.getUsername().equalsIgnoreCase(cNew.getUsername())) {
			cOrigin.setUsername(cNew.getUsername());
		} else if (cOrigin.getBirthDate() != cNew.getBirthDate()) {
			cOrigin.setBirthDate(cNew.getBirthDate());
		}
		return PANELPERFIL_VIEW;
	}

	@GetMapping("/auth/cookAprendiz/formRecipe")
	public String FormRecipe(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", cookService.findById(userDetails.getId()));
		model.addAttribute("ingredients", ingredientService.getListIngredients());
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		return FORMRECIPE_VIEW;
	}

	@PostMapping("/auth/cook/addRecipe")
	public String AddRecipe(recipeModel r) {
        recipeService.addRecipe(r);
		return PANEL_VIEW;

	}

}