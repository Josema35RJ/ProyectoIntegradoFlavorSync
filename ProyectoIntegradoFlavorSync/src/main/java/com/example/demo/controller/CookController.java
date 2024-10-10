package com.example.demo.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;
import com.example.demo.service.RecipeService;

@Controller
public class CookController {

	private static final String PANEL_VIEW = "/auth/cook/cookPanel";
	private static final String PANELPERFIL_VIEW = "/auth/cook/cookPerfil";
	private static final String FORMRECIPE_VIEW = "/auth/cook/formRecipe";
	private static final String UPDATERECIPE_VIEW = "/auth/cook/updateRecipe";
	private static final String COOKRECIPES_VIEW = "/auth/cook/cookRecipes";

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;

	@Autowired
	@Qualifier("recipeService")
	private RecipeService recipeService;

	@Autowired
	@Qualifier("culinaryTechniquesService")
	private CulinaryTechniquesService culinaryTechniquesService;

	@GetMapping("/auth/cook/cookPanel")
	public String PanelCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		model.addAttribute("cook", c.getNickName());
		model.addAttribute("imageCook", c.getImagePerfil());
		model.addAttribute("recipes", recipeService.getListRecipe());
		return PANEL_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}
	
	@GetMapping("/auth/cook/cookRecipes")
	public String RecipesCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		model.addAttribute("cook", c.getNickName());
		byte[] imageBytes = c.getImagePerfil();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		model.addAttribute("base64Image","data:image/jpeg;base64," +  base64Image);
		model.addAttribute("recipes", c.getListRecipes());
		return  COOKRECIPES_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cook/cookPerfil")
	public String PerfilCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());

		byte[] imageBytes = c.getImagePerfil();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);

		model.addAttribute("cookPerfil", c);
		return PANELPERFIL_VIEW;
	}

	@PostMapping("auth/cook/UpdatePerfil")
	public String UpdateCook(cookModel cNew,  RedirectAttributes flash) {
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
		
		flash.addFlashAttribute("success", "¡Cocinero Actualizado exitosamente!");
		return PANELPERFIL_VIEW;
	}

	@GetMapping("/auth/cook/formRecipe")
	public String FormRecipe(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", cookService.findById(userDetails.getId()));
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		return FORMRECIPE_VIEW;
	}

	@PostMapping("/auth/cook/addRecipe")
	public String AddRecipe(recipeModel recipe, RedirectAttributes flash, @RequestParam("imagenRecipeBase64") String imagenR, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		// Convertir la imagen en Base64 a byte[]
		byte[] imageBytes = Base64.getDecoder().decode(imagenR);
	    recipe.setImageRecipePerfil(imageBytes); // Almacena la imagen en byte[] en la entidad recipe
	   
		recipeService.addRecipe(recipe, c);
		
		flash.addFlashAttribute("success", "¡Receta registrada exitosamente!");
		return "redirect:" + COOKRECIPES_VIEW;

	}
	
	@GetMapping("/auth/cook/updateRecipe/{id}")
	public String UpdateRecipe(@PathVariable("id") Integer id, Model model, Authentication authentication, RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		recipeModel r =  recipeService.getRecipeById(id);
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", cookService.findById(userDetails.getId()));

		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		model.addAttribute("recipe", r);
		String n = Base64.getEncoder().encodeToString(r.getImageRecipePerfil());
		model.addAttribute("imageRecipePerfil", n);
		flash.addFlashAttribute("success", "¡Receta Actualizada exitosamente!");
		return UPDATERECIPE_VIEW;
	}

}