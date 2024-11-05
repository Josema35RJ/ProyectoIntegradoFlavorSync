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

import com.example.demo.model.commentModel;
import com.example.demo.model.cookModel;
import com.example.demo.model.culinaryTechniquesModel;
import com.example.demo.model.recipeModel;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CommentService;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;
import com.example.demo.service.RecipeService;
import com.example.demo.service.TokenService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CookController {

	private static final String PANEL_VIEW = "/auth/cook/cookPanel";
	private static final String PANELPERFIL_VIEW = "/auth/cook/cookPerfil";
	private static final String FORMRECIPE_VIEW = "/auth/cook/formRecipe";
	private static final String UPDATERECIPE_VIEW = "/auth/cook/updateRecipe";
	private static final String COOKRECIPES_VIEW = "/auth/cook/cookRecipes";
	private static final String RESETPASSWORD_VIEW = "/auth/resetPassword";
	private static final String RECIPE_VIEW = "/auth/cook/viewRecipe";

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;

	@Autowired
	@Qualifier("commentService")
	private CommentService commentService;

	@Autowired
	@Qualifier("recipeService")
	private RecipeService recipeService;

	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;

	@Autowired
	@Qualifier("culinaryTechniquesService")
	private CulinaryTechniquesService culinaryTechniquesService;

	@GetMapping("/auth/cook/cookPanel")
	public String PanelCook(@RequestParam(required = false) String ingredients,
			@RequestParam(required = false) String category, @RequestParam(required = false) String difficulty,
			@RequestParam(required = false) Integer rating, Model model, Authentication authentication) {

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		cookModel c = cookService.findById(userDetails.getId());

		// Aplicar los filtros al obtener la lista de recetas
		List<recipeModel> recipesFilters = recipeService.filterRecipes(ingredients, category, difficulty, rating);
		List<recipeModel> recetasFacil = recipeService.getListFindByDificulty("Fácil");

		// Mapa de imágenes para las recetas filtradas
		Map<Integer, String> imagesRecipes = new HashMap<>();
		for (recipeModel r : recipesFilters) {
			imagesRecipes.put(r.getId(),
					"data:image/jpeg;base64," + Base64.getEncoder().encodeToString(r.getImageRecipePerfil()));
		}
		model.addAttribute("base64listImageFilters", imagesRecipes);

		// Mapa de imágenes para recetas de dificultad "Fácil"
		Map<Integer, String> imagesRecipesNew = new HashMap<>();
		for (recipeModel r : recetasFacil) {
			imagesRecipesNew.put(r.getId(),
					"data:image/jpeg;base64," + Base64.getEncoder().encodeToString(r.getImageRecipePerfil()));
		}
		model.addAttribute("base64listImageEasy", imagesRecipesNew);

		model.addAttribute("cook", c.getNickName());
		model.addAttribute("imageCook", c.getImagePerfil());
		model.addAttribute("recipesFilters", recipesFilters); // Recetas filtradas
		model.addAttribute("recipesEasy", recetasFacil); // Recetas fáciles

		return PANEL_VIEW; // Asegúrate de que PANEL_VIEW sea el nombre correcto de la vista del panel
	}

	@GetMapping("/auth/cook/cookRecipes")
	public String RecipesCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		Map<Integer, String> imagesRecipes = new HashMap<>();
		cookModel c = cookService.findById(userDetails.getId());
		model.addAttribute("cook", c.getNickName());
		byte[] imageBytes = c.getImagePerfil();
		for (recipeModel r : c.getListRecipes()) {

			imagesRecipes.put(r.getId(),
					"data:image/jpeg;base64," + Base64.getEncoder().encodeToString(r.getImageRecipePerfil()));
		}
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		model.addAttribute("base64listImage", imagesRecipes);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);
		model.addAttribute("recipes", c.getListRecipes());

		return COOKRECIPES_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cook/viewRecipe/{id}")
	public String ViewRecipe(@PathVariable("id") Integer id, Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		List<String> listImagesRecipe = new ArrayList<>();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		cookModel c = cookService.findById(userDetails.getId());
		recipeModel r = recipeService.getRecipeById(id);
		for (byte[] b : r.getImagesRecipe()) {
			listImagesRecipe.add("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(b));
		}
		model.addAttribute("booleanComment", !commentService.findByUserId(c)); // Hay que añadir al comentario el autor
																				// de este
		model.addAttribute("booleanFav", !c.getListRecipesFavorites().contains(r));
		model.addAttribute("imageRecipe",
				"data:image/jpeg;base64," + Base64.getEncoder().encodeToString(r.getImageRecipePerfil()));
		model.addAttribute("listImagesRecipe", listImagesRecipe);
		model.addAttribute("recipe", r);
		return RECIPE_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cook/cookPerfil")
	public String PerfilCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		List<Integer> selectedTechniques = c.getListCulinaryTechniques().stream().map(technique -> technique.getId()) // Asegurarse
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

	@PostMapping("/auth/cook/AddComment")
	public String AddComment(RedirectAttributes flash, @RequestParam(value = "id") String recipeId,
			@RequestParam(value = "commentText") String comment, @RequestParam(value = "rating") String ra,
			Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		commentModel com = new commentModel(comment, Integer.valueOf(ra), cookService.findById(userDetails.getId()));
		recipeModel r = recipeService.getRecipeById(Integer.valueOf(recipeId));
		commentService.addComment(com, Integer.valueOf(recipeId));
		r.getListComments().add(com);
		recipeService.updateRecipe(Integer.valueOf(recipeId), r);
		flash.addFlashAttribute("success", "¡Comentario registrado exitosamente!");
		return "redirect:" + PANEL_VIEW;

	}

	@PostMapping("/auth/cook/updateRecipe")
	public String UpdateRecipePost(recipeModel r, Model model,
			@RequestParam(value = "RecipeTechniques", required = false) String[] listTechniques,
			@RequestParam("imagenPerfilBase64") String imagenPerfilRecipe,
			@RequestParam(value = "recipeImagesBase64", required = false) String[] ImagesBase64,
			Authentication authentication, RedirectAttributes flash) {
		List<culinaryTechniquesModel> l = new ArrayList<>();
		for (String i : listTechniques) {
			l.add(culinaryTechniquesService.findById(Integer.valueOf(i)));
		}
		r.setListRecipeTechniques(l);
		recipeService.updateRecipe(r.getId(), r, imagenPerfilRecipe, ImagesBase64);
		flash.addFlashAttribute("success", "¡Receta actualizada exitosamente!");

		return "redirect:" + PANEL_VIEW;
	}

	@GetMapping("/auth/cook/updateRecipe/{id}")
	public String UpdateRecipe(@PathVariable("id") Integer id, Model model, Authentication authentication,
			RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		List<String> listImagesRecipe = new ArrayList<>();
		recipeModel r = recipeService.getRecipeById(id);
		cookModel c = cookService.findById(userDetails.getId());
		for (byte[] b : r.getImagesRecipe()) {
			listImagesRecipe.add("data:image/jpeg;base64," + Base64.getEncoder().encodeToString(b));
		}
		// Obtener los IDs de las técnicas culinarias seleccionadas previamente en la
		// receta
		List<Integer> selectedTechniques = r.getListRecipeTechniques().stream().map(technique -> technique.getId()) // Asegurarse
																													// de
				// IDs
				.collect(Collectors.toList());

		// Añadir las técnicas seleccionadas y las técnicas disponibles al modelo
		byte[] imageBytes = r.getImageRecipePerfil();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", c);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + base64Image);
		model.addAttribute("imageRecipe", listImagesRecipe);
		model.addAttribute("country", r.getCountry());
		model.addAttribute("city", r.getCity());
		model.addAttribute("selectedTechniques", selectedTechniques); // Lista de IDs seleccionados
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		model.addAttribute("recipe", r);

		return UPDATERECIPE_VIEW;
	}

	@PostMapping("/auth/cook/updatePassword")
	public String updatePassword(@RequestParam("newPassword") String newP,
			@RequestParam("confirmPassword") String confirmP, HttpSession session, Model model,
			Authentication authentication, RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		cookService.updatePassword(newP, cookService.findById(userDetails.getId()));
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		flash.addFlashAttribute("success", "¡Contraseña Actualizada exitosamente!");
		return "redirect:" + PANELPERFIL_VIEW;

	}

	@PostMapping("/auth/resetPassword")
	public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes flash) {

		// Check if the new password and confirm password match
		if (!newPassword.equals(confirmPassword)) {
			flash.addFlashAttribute("error", "Las contraseñas no coinciden.");
			return "redirect:/auth/reset-password?token=" + token; // Redirect back to the reset password page
		}
		String email = tokenService.getEmailFromToken(token);
		// Validate the token
		if (tokenService.isTokenValid(token)) {
			flash.addFlashAttribute("error", "Token inválido o expirado.");
			return "redirect:/auth/login"; // Redirect to login or another appropriate page
		}
		// Update the password using the UserService
		cookService.updatePassword(newPassword, cookService.findByUsername(email)); // Implement this method in
																					// UserService
		tokenService.cleanupExpiredTokens();
		flash.addFlashAttribute("success", "¡Contraseña actualizada exitosamente!");
		return "redirect:/auth/login"; // Redirect to login after successful password update
	}

	@GetMapping("/auth/resetPassword/{token}")
	public String ResetRecipe(@PathVariable("token") String token, Model model, RedirectAttributes flash) {
		model.addAttribute("token", token);
		return RESETPASSWORD_VIEW;
	}

	@PostMapping("/auth/cook/favoriteRecipe/{recipeId}")
	public String favRecipe(@PathVariable("recipeId") Integer rId, Authentication authentication,
			RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		recipeService.toggleFav(rId, userDetails.getId());
		flash.addFlashAttribute("success", "¡Añadido correctamente exitosamente!");
		return "redirect:" + PANEL_VIEW; // Redirect to login after successful password update
	}

	private int calculateAge(LocalDate birthDate) {
		LocalDate today = LocalDate.now();
		return Period.between(birthDate, today).getYears();
	}

}