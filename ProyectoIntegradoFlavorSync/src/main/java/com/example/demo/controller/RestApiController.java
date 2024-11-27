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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.cookModel;
import com.example.demo.model.culinaryTechniquesModel;
import com.example.demo.model.recipeModel;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;
import com.example.demo.service.RecipeService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class RestApiController {

	@Autowired
	private CookService cookService;

	@Autowired
	private RecipeService recipeService;

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

	@GetMapping("/api/auth/cookapp/Cook")
	public ResponseEntity<?> getCookData(@RequestParam("id") int id) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Suponiendo que tienes un servicio para obtener el Cook por su ID
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
