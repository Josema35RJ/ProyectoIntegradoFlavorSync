package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.cookModel;
import com.example.demo.model.culinaryTechniquesModel;
import com.example.demo.model.recipeModel;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;
import com.example.demo.service.RecipeService;
import com.example.demo.service.impl.EmailServiceImpl;
import com.example.demo.service.impl.TokenServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class RestApiController {

	@Autowired
	private CookService cookService;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private TokenServiceImpl tokenService;
	
	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private CulinaryTechniquesService culinaryTechniquesService;

	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		Map<String, Object> response = new HashMap<>();
		try {
			cookModel c = cookService.findByUsernameAndPassword(username, password);
			// Generar el token JWT
			String token = getJWTToken(username, password);
			c.setToken(token);

			// Crear un mapa con los campos específicos que deseas incluir en la respuesta
			Map<String, Object> userData = new HashMap<>();
			userData.put("token", c.getToken());
			userData.put("id", c.getId());
			userData.put("ROL", c.getRole());

			response.put("success", true);
			response.put("data", userData);
			response.put("message", "Inicio de sesion exitoso");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DisabledException e) {
			response.put("success", false);
			response.put("message", "El usuario no esta activado");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		} catch (AccountExpiredException e) {
			response.put("success", false);
			response.put("message", "El usuario ha sido borrado");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		} catch (BadCredentialsException e) {
			response.put("success", false);
			response.put("message", "Usuario o clave incorrectos");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al iniciar sesion");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/ListCulinaryTechniques")
	public ResponseEntity<?> ListCulinaryTechniques() {
		Map<String, Object> response = new HashMap<>();
		try {

			List<culinaryTechniquesModel> listCulinaryTechniques = culinaryTechniquesService
					.getListCulinaryTechniques();// Obtener los servicios de la

			response.put("success", true);
			response.put("data", listCulinaryTechniques);
			response.put("message", "Lista obtenida con exito");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/api/auth/cookapp/ListRecipe")
	public ResponseEntity<?> ListRecipe() {
		Map<String, Object> response = new HashMap<>();
		try {
			List<recipeModel> listRecipe = recipeService.getListRecipe();
			response.put("success", true);
			response.put("data", listRecipe);
			response.put("message", "Lista obtenida con exito");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", e);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/api/auth/cookapp/Cook")
	public ResponseEntity<?> getCookData(@RequestBody Map<String, Integer> request) {
		Map<String, Object> response = new HashMap<>();
		try {
			int id = request.get("id"); // Obtener el 'id' del cuerpo
			cookModel cook = cookService.findById(id);

			if (cook != null) {
				response.put("success", true);
				response.put("data", cook);
				response.put("message", "Cook obtenido con éxito");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("success", false);
				response.put("message", "Cook no encontrado");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/api/register")
	public ResponseEntity<?> saveCook(@RequestBody cookModel cook) {
		Map<String, Object> response = new HashMap<>();
		try {
			cookService.registrar(cook);
			// Registrar el cocinero con estado "no verificado"
			cook.setConfirm_email(false); // Indica que el correo aún no ha sido verificado

			// Generar token de verificación
			String token = tokenService.generateToken(cook.getUsername()); // Genera el token usando el email
			tokenService.saveTokenToDatabase(token, cook.getUsername()); // Guarda el token en la base de datos

			// Enviar correo de verificación
			String verificationLink = "https://proyectointegradoflavorsync.onrender.com/verify-email/" + token;
		    emailService.sendVerificationEmail(cook.getUsername(), verificationLink, "Verificación de correo electrónico");
			response.put("success", true);
			response.put("message", "Usuario registrado con exito");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			response.put("success", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al registrar el usuario: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/api/cookweb/addRecipe")
	public ResponseEntity<?> saveRecipe(@RequestBody Map<String, Object> request) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        // Extraer el ID y la receta del JSON
	        int id = (int) request.get("id");
	        Map<String, Object> recipeData = (Map<String, Object>) request.get("recipe");

	        // Convertir el mapa de datos de la receta al objeto recipeModel
	        ObjectMapper mapper = new ObjectMapper();
	        recipeModel r = mapper.convertValue(recipeData, recipeModel.class);

	        // Buscar el cocinero y agregar la receta
	        cookModel cook = cookService.findById(id);
	        recipeService.addRecipe(r, cook);

	        response.put("success", true);
	        response.put("message", "Receta creada con éxito");
	        return new ResponseEntity<>(response, HttpStatus.CREATED);
	    } catch (IllegalArgumentException e) {
	        response.put("success", false);
	        response.put("message", e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "Error al crear receta: " + e.getMessage());
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping("/api/cookweb/updateRecipe")
	public ResponseEntity<?> updateRecipe(@RequestBody Map<String, Integer> request, @RequestBody recipeModel r) {
		Map<String, Object> response = new HashMap<>();
		try {
			recipeService.updateRecipe(r.getId(), r);
			response.put("success", true);
			response.put("message", "Receta creada con exito");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			response.put("success", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Error al crear receta: " + e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private boolean isValidEmailAddress(String email) {
		// Expresión regular para verificar el formato de un correo electrónico
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
		return email.matches(regex);
	}

	private String getJWTToken(String username, String password) {
		String secretKey = "mySecretKey";
		cookModel cook = cookService.findByUsernameAndPassword(username, password);
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(cook.getRole());

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
		return "Bearer " + token;
	}
}
