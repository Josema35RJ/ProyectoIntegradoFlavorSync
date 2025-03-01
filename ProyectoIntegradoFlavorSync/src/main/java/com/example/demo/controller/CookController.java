package com.example.demo.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

import com.example.demo.model.MovieModel;
import com.example.demo.model.commentModel;
import com.example.demo.model.cookModel;
import com.example.demo.model.culinaryTechniquesModel;
import com.example.demo.model.ingredientModel;
import com.example.demo.model.ingredientRecipeModel;
import com.example.demo.model.recipeModel;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CommentService;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;
import com.example.demo.service.IngredientService;
import com.example.demo.service.RecipeService;
import com.example.demo.service.TokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CookController {

	private static final String PANEL_VIEW = "/auth/cook/cookPanel";
	private static final String PANELPERFIL_VIEW = "/auth/cook/cookPerfil";
	private static final String FORMRECIPE_VIEW = "/auth/cook/formRecipe";
	private static final String UPDATERECIPE_VIEW = "/auth/cookweb/updateRecipe";
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
	@Qualifier("ingredientService")
	private IngredientService ingredientService;

	@Autowired
	@Qualifier("recipeService")
	private RecipeService recipeService;

	@Autowired
	@Qualifier("tokenService")
	private TokenService tokenService;

	@Autowired
	@Qualifier("culinaryTechniquesService")
	private CulinaryTechniquesService culinaryTechniquesService;

	@GetMapping("/auth/cookweb/cookPanel")
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
			imagesRecipes.put(r.getId(), "data:image/jpeg;base64," + r.getImageRecipePerfil());
		}
		model.addAttribute("base64listImageFilters", imagesRecipes);

		// Mapa de imágenes para recetas de dificultad "Fácil"
		Map<Integer, String> imagesRecipesNew = new HashMap<>();
		for (recipeModel r : recetasFacil) {
			imagesRecipesNew.put(r.getId(), "data:image/jpeg;base64," + r.getImageRecipePerfil());
		}
		model.addAttribute("base64listImageEasy", imagesRecipesNew);

		model.addAttribute("cook", c.getNickName());
		model.addAttribute("imageCook", c.getImagePerfil());
		model.addAttribute("recipesFilters", recipesFilters); // Recetas filtradas
		model.addAttribute("recipesEasy", recetasFacil); // Recetas fáciles

		return PANEL_VIEW; // Asegúrate de que PANEL_VIEW sea el nombre correcto de la vista del panel
	}

	@GetMapping("/auth/cookweb/cookRecipes")
	public String RecipesCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		Map<Integer, String> imagesRecipes = new HashMap<>();
		cookModel c = cookService.findById(userDetails.getId());
		model.addAttribute("cook", c);
		for (recipeModel r : c.getListRecipes()) {

			imagesRecipes.put(r.getId(), "data:image/jpeg;base64," + r.getImageRecipePerfil());
		}
		model.addAttribute("base64listImage", imagesRecipes);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + c.getImagePerfil());

		return COOKRECIPES_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cookweb/viewRecipe/{id}")
	public String ViewRecipe(@PathVariable("id") Integer id, Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		cookModel c = cookService.findById(userDetails.getId());
		recipeModel r = recipeService.getRecipeById(id);
		cookModel cookRecipe = cookService.findByRecipeId(r);
		model.addAttribute("booleanComment", commentService.findByUserId(c, r));
		model.addAttribute("imagePerfilCook", "data:image/jpeg;base64," + cookRecipe.getImagePerfil());
		model.addAttribute("cookRecipe", cookRecipe);
		model.addAttribute("booleanLike", recipeService.booleanLike(c, r));
		model.addAttribute("booleanFav", cookService.booleanFav(c, r));
		model.addAttribute("imageRecipe", "data:image/jpeg;base64," + r.getImageRecipePerfil());
		model.addAttribute("listImagesRecipe", r.getImagesRecipe());
		model.addAttribute("userSession", c);
		model.addAttribute("booleanCookCreate", cookService.booleanCookCreate(c, r)); // Probar a comparar por id
		model.addAttribute("recipe", r);
		model.addAttribute("video", "data:video/mp4;base64," +r.getVideo().getVideo());
		return RECIPE_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/cookweb/cookPerfil")
	public String PerfilCook(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(userDetails.getId());
		List<Integer> selectedTechniques = c.getListCulinaryTechniques().stream().map(technique -> technique.getId()) // Asegurarse
				.collect(Collectors.toList());

		model.addAttribute("base64Image", "data:image/jpeg;base64," + c.getImagePerfil());
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

	@PostMapping("/auth/cookweb/cookPerfil")
	public String ViewPerfilCook(@RequestParam("id") Integer id, Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		cookModel c = cookService.findById(id);
		List<Integer> selectedTechniques = c.getListCulinaryTechniques().stream().map(technique -> technique.getId()) // Asegurarse
				.collect(Collectors.toList());

		model.addAttribute("base64Image", "data:image/jpeg;base64," + c.getImagePerfil());
		LocalDate birthDate = LocalDate.of(1990, 5, 20); // Ejemplo de fecha de nacimiento
		int age = calculateAge(birthDate);
		model.addAttribute("selectedTechniques", selectedTechniques); // Lista de IDs seleccionados
		model.addAttribute("country", c.getCountry());
		model.addAttribute("city", c.getCity());
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		model.addAttribute("age", age);
		model.addAttribute("cookPerfil", c);
		if (userDetails.getId() != id)
			model.addAttribute("booleanEdit", false);
		else
			model.addAttribute("booleanEdit", true);
		return PANELPERFIL_VIEW;
	}

	@PostMapping("auth/cookweb/UpdatePerfil")
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

		if (imagenPerfil.length() > 0) {
			cOrigin.setImagePerfil(imagenPerfil); // Almacena la imagen en byte[] en la entidad cook
		}
		cookService.updateCook(cOrigin, listCulinaryTechniques);
		flash.addFlashAttribute("success", "¡Cocinero Actualizado exitosamente!");
		return "redirect:/auth/cookweb/cookPerfil";
	}

	@GetMapping("/auth/cookweb/formRecipe")
	public String FormRecipe(Model model, Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", cookService.findById(userDetails.getId()));
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		List<ingredientModel> ingredientes = ingredientService.getListIngredients();
		ingredientes.sort(Comparator.comparing(ingredientModel::getNombre));

		// Agrupar los ingredientes por categoría
		Map<String, List<ingredientModel>> ingredientesPorCategoria = ingredientes.stream()
				.collect(Collectors.groupingBy(ingredientModel::getCategoria));

		// Ordenar las categorías alfabéticamente
		Map<String, List<ingredientModel>> ingredientesPorCategoriaOrdenados = new TreeMap<>(ingredientesPorCategoria);

		model.addAttribute("ingredientesPorCategoria", ingredientesPorCategoriaOrdenados);

		return FORMRECIPE_VIEW;
	}

	@PostMapping("/auth/cookweb/addRecipe")
	public String AddRecipe(recipeModel recipe, MovieModel video, RedirectAttributes flash,
			@RequestParam(value = "RecipeTechniques", required = false) String[] listTechniques,
			@RequestParam(value = "recipeImagesBase64", required = false) String[] ImagesBase64,
			@RequestParam("imagenRecipeBase64") String imagenR, Authentication authentication,
			@RequestParam(value = "ingredientsList") String ingredientsListJson) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		cookModel c = cookService.findById(userDetails.getId());
		// Convertir la imagen en Base64 a byte[]
		recipe.setImageRecipePerfil(imagenR); // Almacena la imagen en byte[] en la entidad recipe
		// Procesar las imágenes adicionales y convertirlas a byte[]

		List<culinaryTechniquesModel> l = Arrays.stream(listTechniques) 
				.filter(technique -> !technique.isBlank()) 
				.map(technique -> culinaryTechniquesService.findById(Integer.valueOf(technique))) 
				.collect(Collectors.toList());

		List<String> lImages = Arrays.stream(ImagesBase64) 
				.filter(image -> !image.isBlank()) 
				.collect(Collectors.toList());

		recipe.setImagesRecipe(lImages);
		recipe.setListRecipeTechniques(l);
		// Deserializar JSON de ingredientes
		ObjectMapper objectMapper = new ObjectMapper();
		List<ingredientRecipeModel> ingredientRecipes = new ArrayList<>();

		try {
			List<Map<String, Object>> parsedIngredients = objectMapper.readValue(ingredientsListJson, List.class);

			for (Map<String, Object> item : parsedIngredients) {
				Long ingredientId = Long.parseLong(item.get("id").toString());
				Double quantity = Double.parseDouble(item.get("quantity").toString());
				String unit = item.get("unit").toString();

				ingredientModel ingredient = ingredientService.findById(ingredientId.intValue());

				if (ingredient != null) {
					ingredientRecipeModel ingredientRecipe = new ingredientRecipeModel(recipe, ingredient, quantity,
							unit);
					ingredientRecipes.add(ingredientRecipe);
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			flash.addFlashAttribute("error", "Hubo un problema procesando los ingredientes.");
			return "redirect:/auth/cookweb/addRecipe";
		}
		recipe.getIngredients().addAll(ingredientRecipes);
        recipe.setVideo(video);
		recipeService.addRecipe(recipe, c);

		flash.addFlashAttribute("success", "¡Receta registrada exitosamente!");
		return "redirect:/auth/cookweb/cookRecipes";
	}

	@PostMapping("/auth/cookweb/AddComment")
	public String AddComment(RedirectAttributes flash, @RequestParam(value = "recipeId") Integer recipeId,
			@RequestParam(value = "cookCommentId") Integer cookCommentId, commentModel c, Authentication authentication,
			HttpServletRequest request) {
		recipeModel r = recipeService.getRecipeById(recipeId);
		c.setCookId(cookService.findById(cookCommentId));
		commentService.addComment(c, recipeId);
		r.getListComments().add(c);
		cookService.verifyPunctuation(cookService.findByRecipeId(r));
		flash.addFlashAttribute("success", "¡Comentario registrado exitosamente!");
		String referer = request.getHeader("Referer");
		// Redirigimos a la misma página
		return "redirect:" + referer;
	}

	@PostMapping("/auth/cookweb/AddReply")
	public String AddReply(RedirectAttributes flash, @RequestParam(value = "recipeId") Integer recipeId,
			@RequestParam(value = "commentId") Integer commentId,
			@RequestParam(value = "cookCommentId") Integer cookCommentId, commentModel c, Authentication authentication,
			HttpServletRequest request) {
		recipeModel r = recipeService.getRecipeById(recipeId);
		c.setParentComment(commentService.findById(commentId));
		c.setCookId(cookService.findById(cookCommentId));
		commentService.addComment(c, recipeId);
		r.getListComments().add(c);
		cookService.verifyPunctuation(cookService.findByRecipeId(r));
		flash.addFlashAttribute("success", "¡Comentario registrado exitosamente!");
		String referer = request.getHeader("Referer");
		// Redirigimos a la misma página
		return "redirect:" + referer;
	}

	@PostMapping("/auth/cookweb/updateRecipe")
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

		return "redirect:/auth/cookweb/cookPanel";
	}

	@PostMapping("/auth/cookweb/formUpdateRecipe")
	public String UpdateRecipe(@RequestParam("id") Integer id, Model model, Authentication authentication,
			RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		List<String> listImagesRecipe = new ArrayList<>();
		recipeModel r = recipeService.getRecipeById(id);
		cookModel c = cookService.findById(userDetails.getId());
		for (String b : r.getImagesRecipe()) {
			listImagesRecipe.add("data:image/jpeg;base64," + b);
		}
		// Obtener los IDs de las técnicas culinarias seleccionadas previamente en la
		// receta
		List<Integer> selectedTechniques = r.getListRecipeTechniques().stream().map(technique -> technique.getId()) // Asegurarse
																													// de
				// IDs
				.collect(Collectors.toList());

		// Ahora puedes obtener la información del usuario logueado desde userDetails
		model.addAttribute("cookPerfil", c);
		model.addAttribute("base64Image", "data:image/jpeg;base64," + r.getImageRecipePerfil());
		model.addAttribute("imageRecipe", listImagesRecipe);
		model.addAttribute("country", r.getCountry());
		model.addAttribute("city", r.getCity());
		model.addAttribute("selectedTechniques", selectedTechniques); // Lista de IDs seleccionados
		model.addAttribute("recipeTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		model.addAttribute("recipe", r);

		return "/auth/cook/updateRecipe";
	}

	@PostMapping("/auth/cookweb/updatePassword")
	public String updatePassword(@RequestParam("newPassword") String newP,
			@RequestParam("confirmPassword") String confirmP, HttpSession session, Model model,
			Authentication authentication, RedirectAttributes flash) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		cookService.updatePassword(newP, cookService.findById(userDetails.getId()));
		// Ahora puedes obtener la información del usuario logueado desde userDetails

		flash.addFlashAttribute("success", "¡Contraseña Actualizada exitosamente!");
		return "redirect:/auth/cookweb/cookPerfil";

	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes flash) {

		// Check if the new password and confirm password match
		if (!newPassword.equals(confirmPassword)) {
			flash.addFlashAttribute("error", "Las contraseñas no coinciden.");
			return "redirect:/reset-password?token=" + token; // Redirect back to the reset password page
		}
		String email = tokenService.getEmailFromToken(token);
		// Validate the token
		if (!tokenService.isTokenValid(token)) {
			flash.addFlashAttribute("error", "Token inválido o expirado.");
			return "redirect:/auth/login"; // Redirect to login or another appropriate page
		}
		// Update the password using the UserService
		cookService.updatePassword(newPassword, cookService.findByUsername(email)); // Implement this method in
																					// UserService
		tokenService.cleanupExpiredTokens();
		flash.addFlashAttribute("success", "¡Contraseña actualizada exitosamente!");
		return "/auth/login"; // Redirect to login after successful password update
	}

	@GetMapping("/resetPassword/{token}")
	public String ResetRecipe(@PathVariable("token") String token, Model model, RedirectAttributes flash) {
		model.addAttribute("token", token);
		return RESETPASSWORD_VIEW;
	}

	@PostMapping("/auth/cookweb/favoriteRecipe")
	public String favRecipe(@RequestParam("recipeId") Integer rId, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		recipeService.toggleFav(rId, userDetails.getId());

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/cookweb/likeRecipe")
	public String likeRecipe(@RequestParam("recipeId") Integer rId, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		recipeService.toggleLike(rId, userDetails.getId());

		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/cookweb/DeleteRecipe")
	public String DeleteRecipe(@RequestParam("id") Integer id, Model model, Authentication authentication,
			RedirectAttributes flash, HttpServletRequest request) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		cookService.deleteRecipeByListCook(userDetails.getId(), id);
		recipeService.deleteRecipe(id);

		flash.addFlashAttribute("success", "¡Receta eliminada exitosamente!");
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@PostMapping("/auth/cookweb/DeleteCook")
	public String DeleteCook(Model model, Authentication authentication, RedirectAttributes flash,
			HttpServletRequest request) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		// Recoge el valor del campo oculto
		String deleteOption = request.getParameter("deleteOption");

		// Decide qué acción realizar según el valor de deleteOption
		if ("onlyCook".equals(deleteOption)) {
			// Lógica para eliminar solo al cocinero
			cookService.deletedCook(userDetails.getId());
			flash.addFlashAttribute("success", "¡Cocinero eliminado exitosamente!");
		} else if ("cookAndRecipes".equals(deleteOption)) {
			// Lógica para eliminar al cocinero y sus recetas
			cookService.deleteCookAndRecipes(userDetails.getId());
			flash.addFlashAttribute("success", "¡Cocinero y recetas eliminados exitosamente!");
		} else {
			flash.addFlashAttribute("error", "Opción de eliminación no válida.");
			return "redirect:/error";
		}

		return "redirect:/auth/cookweb/logout";
	}

	private int calculateAge(LocalDate birthDate) {
		LocalDate today = LocalDate.now();
		return Period.between(birthDate, today).getYears();
	}

}