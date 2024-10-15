package com.example.demo.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.example.demo.model.culinaryTechniquesModel;
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
		List<String> imagesRecipes = new ArrayList<>();
		cookModel c = cookService.findById(userDetails.getId());
		model.addAttribute("cook", c.getNickName());
		byte[] imageBytes = c.getImagePerfil();
		for (recipeModel r : c.getListRecipes()) {

			imagesRecipes.add("data:image/jpeg;base64," +Base64.getEncoder().encodeToString(r.getImageRecipePerfil()));
		}
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		model.addAttribute("base64listImage",  imagesRecipes);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);
		model.addAttribute("recipes", c.getListRecipes());

		return COOKRECIPES_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cook/cookPerfil")
	public String PerfilCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		List<Integer> selectedTechniques = c.getListCulinaryTechniques().stream().map(technique -> technique.getId()) // Asegurarse
		// de
// IDs
				.collect(Collectors.toList());

		byte[] imageBytes = c.getImagePerfil();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);
		LocalDate birthDate = LocalDate.of(1990, 5, 20); // Ejemplo de fecha de nacimiento
		int age = calculateAge(birthDate);
		model.addAttribute("selectedTechniques", selectedTechniques); // Lista de IDs seleccionados
		model.addAttribute("country", c.getCountry());
		model.addAttribute("city", c.getCity());
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		model.addAttribute("age", age);
		model.addAttribute("cookPerfil", c);
		return PANELPERFIL_VIEW;
	}

	@PostMapping("auth/cook/UpdatePerfil")
	public String UpdateCook(cookModel cNew, @RequestParam("culinaryTechniquesIds") List<String> listCulinaryTechniques,
			@RequestParam("imagenPerfilBase64") String imagenPerfil, Authentication authentication,
			RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		cookModel cOrigin = cookService.findById(userDetails.getId());
		if (!cOrigin.getNickName().equalsIgnoreCase(cNew.getNickName())) {
			cOrigin.setNickName(cNew.getNickName());
		}
		if (!cOrigin.getFirstName().equalsIgnoreCase(cNew.getFirstName())) {
			cOrigin.setFirstName(cNew.getFirstName());
		}
		if (!cOrigin.getLastName().equalsIgnoreCase(cNew.getLastName())) {
			cOrigin.setLastName(cNew.getLastName());
		}
		if (!cOrigin.getUsername().equalsIgnoreCase(cNew.getUsername())) {
			cOrigin.setUsername(cNew.getUsername());
		}
		// Comparar fechas correctamente usando equals
		if (!cOrigin.getBirthDate().equals(cNew.getBirthDate())) {
			cOrigin.setBirthDate(cNew.getBirthDate());
		}
		// Asegurarse de que experience sea un tipo primitivo o usar equals() para
		// objetos
		if (cOrigin.getExperience() != cNew.getExperience()) {
			cOrigin.setExperience(cNew.getExperience());
		}
		// Comparar listas correctamente
		if (!new HashSet<>(cOrigin.getListSpecialty()).equals(new HashSet<>(cNew.getListSpecialty()))) {
			cOrigin.setListSpecialty(cNew.getListSpecialty());
		}
		if (!cOrigin.getCountry().equals(cNew.getCountry())) {
			cOrigin.setCountry(cNew.getCountry());
		}
		if (!cOrigin.getCity().equals(cNew.getCity())) {
			cOrigin.setCity(cNew.getCity());
		}

		byte[] imageBytes = Base64.getDecoder().decode(imagenPerfil);
		if (imageBytes.length > 0) {
			cOrigin.setImagePerfil(imageBytes); // Almacena la imagen en byte[] en la entidad cook
		}
		cookService.updateCook(cOrigin, listCulinaryTechniques);
		flash.addFlashAttribute("success", "¡Cocinero Actualizado exitosamente!");
		return "redirect:" + PANELPERFIL_VIEW;
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
	public String AddRecipe(recipeModel recipe, RedirectAttributes flash,
			@RequestParam(value = "RecipeTechniques", required = false) String[] listTechniques,
			@RequestParam(value = "recipeImagesBase64", required = false) String[] ImagesBase64,
			@RequestParam("imagenRecipeBase64") String imagenR, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		// Convertir la imagen en Base64 a byte[]
		byte[] imageBytes = Base64.getDecoder().decode(imagenR);
		recipe.setImageRecipePerfil(imageBytes); // Almacena la imagen en byte[] en la entidad recipe
		// Procesar las imágenes adicionales y convertirlas a byte[]
		List<byte[]> images = new ArrayList<>();
		for (String imageBase64 : ImagesBase64) {
			byte[] imageBytesRecipe = Base64.getDecoder().decode(imageBase64);
			images.add(imageBytesRecipe);
		}
		List<culinaryTechniquesModel> l = new ArrayList<>();
		for (String i : listTechniques) {
			l.add(culinaryTechniquesService.findById(Integer.valueOf(i)));
		}
		recipe.setImagesRecipe(images);
		recipe.setListRecipeTechniques(l);
		recipeService.addRecipe(recipe, c);

		flash.addFlashAttribute("success", "¡Receta registrada exitosamente!");
		return "redirect:" + COOKRECIPES_VIEW;

	}

	@GetMapping("/auth/cook/updateRecipe/{id}")
	public String UpdateRecipe(@PathVariable("id") Integer id, Model model, Authentication authentication,
			RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		recipeModel r = recipeService.getRecipeById(id);
		cookModel c = cookService.findById(userDetails.getId());
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", c);
		// Obtener los IDs de las técnicas culinarias seleccionadas previamente en la
		// receta
		List<Integer> selectedTechniques = r.getListRecipeTechniques().stream().map(technique -> technique.getId()) // Asegurarse
																													// de
				// IDs
				.collect(Collectors.toList());

		// Añadir las técnicas seleccionadas y las técnicas disponibles al modelo
		model.addAttribute("country", r.getCountry());
		model.addAttribute("city", r.getCity());
		model.addAttribute("selectedTechniques", selectedTechniques); // Lista de IDs seleccionados
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		model.addAttribute("recipe", r);

		flash.addFlashAttribute("success", "¡Receta Actualizada exitosamente!");
		return UPDATERECIPE_VIEW;
	}

	private int calculateAge(LocalDate birthDate) {
		LocalDate today = LocalDate.now();
		return Period.between(birthDate, today).getYears();
	}

}