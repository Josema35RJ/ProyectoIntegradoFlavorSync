package com.example.demo.controller;

import java.security.Principal;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.converter.CookConverter;
import com.example.demo.model.cookModel;
import com.example.demo.service.CookService;
import com.example.demo.service.CulinaryTechniquesService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class loginController {

	private static final String LOGIN_VIEW = "/auth/login";
	private static final String REGISTER_VIEW = "/auth/register";

	@Autowired
	@Qualifier("cookService")
	private CookService cookService;

	@Autowired
	@Qualifier("culinaryTechniquesService")
	private CulinaryTechniquesService culinaryTechniquesService;

	@Autowired
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@GetMapping("/auth/login")
	public String login(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout, Principal principal) {

		// Redirige a la página principal si el usuario ya inició sesión
		if (principal != null) {
			return "redirect:/" + LOGIN_VIEW;
		}

		// Solo agrega mensajes de error al modelo si los parámetros correspondientes no
		// están vacíos
		if (error != null) {
			model.addAttribute("loginError", "Error de inicio de sesión. Por favor, verifica tus credenciales.");
			// Agrega otros mensajes de error específicos aquí si es necesario
		} else {
			// Asegúrate de que los atributos del modelo estén limpios si no hay errores
			model.addAttribute("loginError", null);
			model.addAttribute("emailError", null);
			model.addAttribute("passwordError", null);
			model.addAttribute("accountLocked", null);
			model.addAttribute("accountNotActivated", null);
		}

		if (logout != null) {
			model.addAttribute("logoutMessage", "Has cerrado sesión con éxito. ¡Esperamos verte pronto!");
		} else {
			// Limpia el mensaje de cierre de sesión si no hay logout
			model.addAttribute("logoutMessage", null);
		}

		// Retorna la vista de login
		return LOGIN_VIEW; // Asegúrate de que LOGIN_VIEW sea el nombre correcto de la vista de login
	}

	@GetMapping("/auth/register")
	public String registerForm(Model model) {
		model.addAttribute("cook", new cookModel());
		model.addAttribute("listCulinaryTechniques", culinaryTechniquesService.getListCulinaryTechniques());
		return REGISTER_VIEW;
	}

	@PostMapping("/auth/register")
	public String registerSubmit(@ModelAttribute("cookModel") cookModel cook,
	                             @RequestParam("listCulinaryTechniquesIds") List<String> culinaryTechniquesIds,
	                             @RequestParam("imagenPerfil") String imagenPerfil, // Recibe la imagen en Base64
	                             @RequestParam("confirmPassword") String confirmPassword, BindingResult result, RedirectAttributes flash) {

	    // Validaciones existentes
	    if (result.hasErrors()) {
	        return REGISTER_VIEW;
	    }

	    
	    // Convertir la imagen en Base64 a byte[]
	    byte[] imageBytes = Base64.getDecoder().decode(imagenPerfil);
	    cook.setImagePerfil(imageBytes); // Almacena la imagen en byte[] en la entidad cook

	    // Restante de la lógica de validación
	    cook.setUsername(cook.getUsername().toLowerCase());

	    if (cook.getFirstName().length() > 25) {
	        flash.addFlashAttribute("error", "¡El nombre excede los 25 caracteres!");
	        return "redirect:" + REGISTER_VIEW;
	    } else if (cook.getLastName().length() > 50) {
	        flash.addFlashAttribute("error", "¡Los apellidos exceden los 50 caracteres!");
	        return "redirect:" + REGISTER_VIEW;
	    } else if (cook.getExperience() < 0) {
	        flash.addFlashAttribute("error", "¡La experiencia debe ser mayor o igual a 0!");
	        return "redirect:" + REGISTER_VIEW;
	    } else if (cookService.existeUsername(cook.getUsername())) {
	        flash.addFlashAttribute("error", "Este correo electrónico ya está registrado");
	        return "redirect:" + REGISTER_VIEW;
	    } else if (!isValidEmailAddress(cook.getUsername())) {
	        flash.addFlashAttribute("error", "¡El correo electrónico no tiene un formato válido!");
	        return "redirect:" + REGISTER_VIEW;
	    } else if (!cook.getPassword().equals(confirmPassword)) {
	        flash.addFlashAttribute("error", "¡Las contraseñas no coinciden!");
	        return "redirect:" + REGISTER_VIEW;
	    } else if (cook.getPassword().length() < 6 || cook.getPassword().length() > 18) {
	        flash.addFlashAttribute("error", "¡La contraseña debe tener entre 6 y 18 caracteres!");
	        return "redirect:" + REGISTER_VIEW;
	    }
	    
	    // Registrar el cocinero
	    cookService.registrar(cook, culinaryTechniquesIds);
	    flash.addFlashAttribute("success", "¡Cocinero registrado exitosamente!");

	    return "redirect:" + LOGIN_VIEW;
	}
	
	@GetMapping("/auth/cook/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalida la sesión actual
		request.getSession().invalidate();
		// Redirige a la página de inicio de sesión
		return "redirect:" + LOGIN_VIEW;
	}


	private boolean isValidEmailAddress(String email) {
		// Expresión regular para verificar el formato de un correo electrónico
		String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
		return email.matches(regex);
	}

}