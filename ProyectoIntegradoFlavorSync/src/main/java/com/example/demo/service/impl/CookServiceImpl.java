package com.example.demo.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CookConverter;
import com.example.demo.converter.CulinaryTechniquesConverter;
import com.example.demo.converter.RecipeConverter;
import com.example.demo.entity.cook;
import com.example.demo.entity.culinaryTechniques;
import com.example.demo.entity.recipe;
import com.example.demo.model.cookModel;
import com.example.demo.model.culinaryTechniquesModel;
import com.example.demo.model.recipeModel;
import com.example.demo.repository.CookRepository;
import com.example.demo.repository.CulinaryTechniquesRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CookService;

@Service("cookService")
public class CookServiceImpl implements UserDetailsService, CookService {

	@Autowired
	@Qualifier("cookRepository")
	private CookRepository cookRepository;

	@Autowired
	@Qualifier("recipeRepository")
	private RecipeRepository recipeRepository;

	@Autowired
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@Autowired
	@Qualifier("recipeConverter")
	private RecipeConverter recipeConverter;

	@Autowired
	@Qualifier("culinaryTechniquesRepository")
	private CulinaryTechniquesRepository culinaryTechniquesRepository;

	@Autowired
	@Qualifier("culinaryTechniquesConverter")
	private CulinaryTechniquesConverter culinaryTechniquesConverter;

	@Override
	public boolean deletedCook(int id) {
		// TODO Auto-generated method stub
		cookModel c = cookConverter.transform(cookRepository.findByid(id));
		c.getImagesCook().clear();
		c.getListCulinaryTechniques().clear();
		cookRepository.delete(cookConverter.transform(c));
		return true;
	}

	@Override
	public boolean updateCook(cookModel cook, List<String> culinaryTechniquesIds) {
		// TODO Auto-generated method stub
		List<culinaryTechniques> l = new ArrayList<>();
		for (String x : culinaryTechniquesIds) {
			l.add(culinaryTechniquesRepository.findById(x).get());
		}

		cook.setUpdateDate(LocalDateTime.now());
		cook c = cookConverter.transform(cook);
		c.setListCulinaryTechniques(l);
		cookRepository.save(c);
		return true;
	}

	@Override
	public List<cookModel> getAllCooks() {
		// TODO Auto-generated method stub
		List<cookModel> ListModel = new ArrayList<>();
		for (cook c : cookRepository.findAll()) {

			ListModel.add(cookConverter.transform(c));
		}
		return ListModel;
	}

	@Override
	public List<cookModel> getFindByCooksChefs() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksChefs = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Chef".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksChefs.add(cookConverter.transform(c));
		}
		return ListModelCooksChefs;

	}

	@Override
	public List<cookModel> getFindByCooksProfessionals() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksProfesionals = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Profesional".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksProfesionals.add(cookConverter.transform(c));
		}
		return ListModelCooksProfesionals;

	}

	@Override
	public List<cookModel> getFindByCooksAprendiz() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksAprendiz = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Aprendiz".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksAprendiz.add(cookConverter.transform(c));
		}
		return ListModelCooksAprendiz;
	}

	@Override
	public List<cookModel> getFindByCooksAmateurs() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksAmateurs = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Amateurs".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksAmateurs.add(cookConverter.transform(c));
		}
		return ListModelCooksAmateurs;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomUserDetails builder = null;
		cook user = cookRepository.findByUsername(email);

		if (user != null) {
			if (user.isEnabled()) {
				builder = new CustomUserDetails(user.getFirstName(), user.getPassword(),
						Collections.singletonList(new SimpleGrantedAuthority(user.getRole())), user.getId());
			} else {
				throw new DisabledException("El cocinero no está activado");
			}
		} else {
			throw new UsernameNotFoundException("Cocinero no encontrado con el email: " + email);
		}

		return builder;
	}

	@Override
	public boolean existeUsername(String username) {
		// TODO Auto-generated method stub
		if (cookRepository.findByUsername(username) == null)
			return false;
		return true;
	}

	@Override
	public cookModel findById(int id) {
		// TODO Auto-generated method stub
		return cookConverter.transform(cookRepository.findByid(id));
	}

	@Override
	public cookModel findByUsername(String username) {
		// TODO Auto-generated method stub
		return cookConverter.transform(cookRepository.findByUsername(username));
	}

	@Override
	public boolean registrar(cookModel cook, List<String> culinaryTechniquesIds) {
		// TODO Auto-generated method stub
		List<culinaryTechniques> l = new ArrayList<>();
		for (String x : culinaryTechniquesIds) {
			l.add(culinaryTechniquesRepository.findById(x).get());
		}
		cook.setPassword(passwordEncoder().encode(cook.getPassword()));
		cook.setEnabled(false);
		cook.setRole("ROL_COOKAPRENDIZ");
		cook c = cookConverter.transform(cook);
		c.setListCulinaryTechniques(l);
		c.setCreateDate(LocalDateTime.now());
		cookRepository.save(c);
		return true;
	}

	// Método para verificar el email del usuario cambiando su estado a verificado
	@Override
	public void verifyUserEmail(String email) {
		// Buscar el usuario por email
		cook cook = cookRepository.findByUsername(email);
		if (cook != null) {
			cook.setEnabled(true);
			cook.setConfirm_email(true); // Cambia el estado a verificado
			cookRepository.save(cook); // Guarda los cambios en la base de datos
		}
	}

	@Override
	public boolean updatePassword(String newP, cookModel cook) {
		// TODO Auto-generated method stub
		cook.setPassword(passwordEncoder().encode(newP));
		cookRepository.save(cookConverter.transform(cook));
		return true;
	}

	// Método para obtener la lista de usuarios no verificados con tokens expirados
	@Override
	public List<cookModel> findUnverifiedCooks(LocalDateTime currentDateTime) {
		List<cookModel> listcModel = new ArrayList<>();
		for (cook c : cookRepository.findAll()) {

			if (!c.isConfirm_email() && hasPassedOneHour(c.getCreateDate())) {
				listcModel.add(cookConverter.transform(c));
			}

		}
		return listcModel;
	}

	private boolean hasPassedOneHour(LocalDateTime createDate) {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(createDate, now);
		return duration.toHours() >= 1;
	}

	@Override
	public cookModel findByRecipeId(recipeModel r) {
		// TODO Auto-generated method stub
		return cookConverter.transform(cookRepository.findByRecipe(recipeConverter.transform(r)));
	}

	@Override
	public void verifyPunctuation(cookModel c) {
		// TODO Auto-generated method stub
		int punctuation = c.getPunctuation();
		for (recipeModel r : c.getListRecipes())
			punctuation += r.getAverageRating();
		punctuation /= c.getListRecipes().size();
		c.setPunctuation(punctuation);
		if (c.getPunctuation() < 5) {
			c.setRole("ROL_COOKAPRENDIZ");
		} else if (c.getPunctuation() > 5 && c.getPunctuation() < 8) {
			c.setRole("ROL_COOKPROFESIONAL");
		} else if (c.getPunctuation() > 8 && c.getPunctuation() < 10) {
			c.setRole("ROL_COOKCHEF");
		}

		cookRepository.save(cookConverter.transform(c));
	}

	@Override
	public cookModel findByUsernameAndPassword(String username, String password) {
		cookModel c = cookConverter.transform(cookRepository.findByUsername(username));

		if (c != null && passwordEncoder().matches(password, c.getPassword())) {
			return c;
		}

		return new cookModel();
	}

	@Override
	public cookModel registrar(cookModel cook) {
		// TODO Auto-generated method stub
		List<culinaryTechniques> l = new ArrayList<>();
		for (culinaryTechniquesModel x : cook.getListCulinaryTechniques()) {
			l.add(culinaryTechniquesRepository.findById(x.getId()).get());
		}
		cook.setPassword(passwordEncoder().encode(cook.getPassword()));
		cook.setEnabled(false);
		cook.setRole("ROL_COOKAPRENDIZ");
		cook.setCreateDate(LocalDateTime.now());
		cook c = cookConverter.transform(cook);
		c.setListCulinaryTechniques(l);
		cookRepository.save(c);
		return cook;
	}

	@Override
	public boolean booleanFav(cookModel c, recipeModel r) {
		// TODO Auto-generated method stub
		for (recipeModel rI : c.getListRecipesFavorites()) {
			if (rI.getId() == r.getId())
				return true;
		}
		return false;
	}

	@Override
	public boolean updateCook(cookModel cook) {
	    // Fetch the existing cook from the repository using the cook ID
	    cookModel c = cookConverter.transform(cookRepository.findByid(cook.getId()));

	    // Check each field and update if it's different or if it's not null or empty
	    boolean updated = false;

	    // Verificación para evitar campos nulos o vacíos
	    if (cook.getFirstName() != null && !cook.getFirstName().isEmpty() && !cook.getFirstName().equals(c.getFirstName())) {
	        c.setFirstName(cook.getFirstName());
	        updated = true;
	    }
	    if (cook.getLastName() != null && !cook.getLastName().isEmpty() && !cook.getLastName().equals(c.getLastName())) {
	        c.setLastName(cook.getLastName());
	        updated = true;
	    }
	    if (cook.getNickName() != null && !cook.getNickName().isEmpty() && !cook.getNickName().equals(c.getNickName())) {
	        c.setNickName(cook.getNickName());
	        updated = true;
	    }
	    if (cook.getUsername() != null && !cook.getUsername().isEmpty() && !cook.getUsername().equals(c.getUsername())) {
	        c.setUsername(cook.getUsername());
	        updated = true;
	    }
	    if (cook.getBirthDate() != null && !cook.getBirthDate().equals(c.getBirthDate())) {
	        c.setBirthDate(cook.getBirthDate());
	        updated = true;
	    }
	    if (cook.getCity() != null && !cook.getCity().isEmpty() && !cook.getCity().equals(c.getCity())) {
	        c.setCity(cook.getCity());
	        updated = true;
	    }
	    if (cook.getCountry() != null && !cook.getCountry().isEmpty() && !cook.getCountry().equals(c.getCountry())) {
	        c.setCountry(cook.getCountry());
	        updated = true;
	    }
	    if (cook.getPassword() != null && !cook.getPassword().isEmpty() && !cook.getPassword().equals(c.getPassword())) {
	        c.setPassword(cook.getPassword());
	        updated = true;
	    }
	    if (cook.getListSpecialty() != null && !cook.getListSpecialty().equals(c.getListSpecialty())) {
	        c.setListSpecialty(cook.getListSpecialty());
	        updated = true;
	    }
	    if (cook.getListCulinaryTechniques() != null && !cook.getListCulinaryTechniques().equals(c.getListCulinaryTechniques())) {
	        c.setListCulinaryTechniques(cook.getListCulinaryTechniques());
	        updated = true;
	    }
	    if (cook.getExperience() != c.getExperience()) {
	        c.setExperience(cook.getExperience());
	        updated = true;
	    }
	    if (cook.getImagePerfil() != null && !cook.getImagePerfil().equals(c.getImagePerfil())) {
	        c.setImagePerfil(cook.getImagePerfil());
	        updated = true;
	    }
	    if (cook.getImagesCook() != null && !cook.getImagesCook().equals(c.getImagesCook())) {
	        c.setImagesCook(cook.getImagesCook());
	        updated = true;
	    }
	    if (cook.getCreateDate() != null && !cook.getCreateDate().equals(c.getCreateDate())) {
	        c.setCreateDate(cook.getCreateDate());
	        updated = true;
	    }
	    if (cook.getToken() != null && !cook.getToken().equals(c.getToken())) {
	        c.setToken(cook.getToken());
	        updated = true;
	    }

	    // If any field was updated, save the changes to the repository
	    if (updated) {
	    	c.setUpdateDate(LocalDateTime.now());
	        cookRepository.save(cookConverter.transform(c));
	    }

	    return updated; // Return whether the cook was updated
	}



	@Override
	public boolean booleanCookCreate(cookModel c, recipeModel r) {
		// TODO Auto-generated method stub
		boolean f = false;
		for(recipeModel rm :c.getListRecipes()) {
			if(rm.getId()==r.getId())
				f=true;
		}
		return f;
	}

	@Override
	public boolean deleteRecipeByListCook(int cookId, Integer recipeId) {
	    cook cook = cookRepository.findByid(cookId);
	    if (cook == null) {
	        return false; // Manejo básico si no se encuentra el usuario.
	    }

	    // Eliminar de la lista de favoritos de forma segura
	    cook.getListRecipesFavorites().removeIf(recipe -> recipe.getId().equals(recipeId));

	    // Eliminar de la lista personal de recetas de forma segura
	    cook.getListRecipes().removeIf(recipe -> recipe.getId().equals(recipeId));

	    // Guardar cambios en la base de datos
	    cookRepository.save(cook);
	    return true;
	}

	@Override
	public boolean deleteCookAndRecipes(int id) {
		// TODO Auto-generated method stub
		cookModel c = cookConverter.transform(cookRepository.findByid(id));
		c.getImagesCook().clear();
		c.getListCulinaryTechniques().clear();
		c.getListRecipes().clear();
		cookRepository.delete(cookConverter.transform(c));
		return true;
	}
}
