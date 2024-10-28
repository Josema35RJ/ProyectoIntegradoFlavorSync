package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CookConverter;
import com.example.demo.converter.RecipeConverter;
import com.example.demo.entity.cook;
import com.example.demo.entity.recipe;
import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;
import com.example.demo.repository.CookRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;

import jakarta.persistence.EntityNotFoundException;

@Service("recipeService")
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	@Qualifier("recipeRepository")
	private RecipeRepository recipeRepository;

	@Autowired
	@Qualifier("cookRepository")
	private CookRepository cookRepository;

	@Autowired
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@Autowired
	@Qualifier("recipeConverter")
	private RecipeConverter recipeConverter;

	@Override
	public boolean addRecipe(recipeModel recipe, cookModel cook) {
		// Establecer propiedades adicionales
		String[] level = cook.getRole().toString().split("K");
		recipe.setLevel(level[1]);
		recipe.setName(recipe.getName().toUpperCase());
		recipe.setCreateDate(LocalDate.now());
		// Luego agregarla a la lista de recetas del cocinero y guardar la entidad cook
		cook.getListRecipes().add(recipe);
		cookRepository.save(cookConverter.transform(cook));

		return true;
	}

	@Override
	public boolean updateRecipe(int id, recipeModel re) {
		// Buscar la receta existente en el repositorio
		recipeModel recipeOld = recipeConverter.transform(recipeRepository.findById(id));
		if (recipeOld.toString().isEmpty()) {
			// Si no se encuentra la receta, retornar false
			return false;
		}

		// Actualizar nombre si es diferente
		if (!re.getName().equalsIgnoreCase(recipeOld.getName())) {
			recipeOld.setName(re.getName());
		}

		// Actualizar cantidad de comensales si es diferente
		if (re.getDiners() != recipeOld.getDiners()) {
			recipeOld.setDiners(re.getDiners());
		}

		// Actualizar tiempo de preparación si es diferente
		if (re.getPreparationTime() != recipeOld.getPreparationTime()) {
			recipeOld.setPreparationTime(re.getPreparationTime());
		}

		// Actualizar lugar de elaboración si es diferente
		if (!re.getWhereItisDone().equals(recipeOld.getWhereItisDone())) {
			recipeOld.setWhereItisDone(re.getWhereItisDone());
		}

		// Actualizar categorías si son diferentes
		if (!re.getCategory().equals(recipeOld.getCategory())) {
			recipeOld.setCategory(re.getCategory());
		}

		// Actualizar dificultad si es diferente
		if (!re.getDifficulty().equalsIgnoreCase(recipeOld.getDifficulty())) {
			recipeOld.setDifficulty(re.getDifficulty());
		}

		// Actualizar las técnicas culinarias si son diferentes
		if (!re.getListRecipeTechniques().equals(recipeOld.getListRecipeTechniques())) {
			recipeOld.setListRecipeTechniques(re.getListRecipeTechniques());
		}

		// Actualizar utensilios de cocina si son diferentes
		if (!re.getListkitchenUtensils().equals(recipeOld.getListkitchenUtensils())) {
			recipeOld.setListkitchenUtensils(re.getListkitchenUtensils());
		}

		// Actualizar restricciones alimentarias si son diferentes
		if (!re.getAllergensAndDietaryRestrictions().equals(recipeOld.getAllergensAndDietaryRestrictions())) {
			recipeOld.setAllergensAndDietaryRestrictions(re.getAllergensAndDietaryRestrictions());
		}

		// Actualizar la información nutricional si es diferente
		if (re.getNutritionalInformation() != null
				&& !re.getNutritionalInformation().equals(recipeOld.getNutritionalInformation())) {
			recipeOld.setNutritionalInformation(re.getNutritionalInformation());
		}

		// Actualizar las instrucciones si son diferentes
		if (!re.getInstructions().equals(recipeOld.getInstructions())) {
			recipeOld.setInstructions(re.getInstructions());
		}

		// Actualizar los ingredientes si son diferentes
		if (!re.getIngredients().equals(recipeOld.getIngredients())) {
			recipeOld.setIngredients(re.getIngredients());
		}

		// Actualizar las imágenes si son diferentes
		if (!re.getImagesRecipe().equals(recipeOld.getImagesRecipe())) {
			recipeOld.setImagesRecipe(re.getImagesRecipe());
		}

		// Actualizar la imagen de perfil si es diferente
		if (re.getImageRecipePerfil() != null
				&& !Arrays.equals(re.getImageRecipePerfil(), recipeOld.getImageRecipePerfil())) {
			recipeOld.setImageRecipePerfil(re.getImageRecipePerfil());
		}

		// Actualizar el historial si es diferente
		if (!re.getHistory().equals(recipeOld.getHistory())) {
			recipeOld.setHistory(re.getHistory());
		}

		// Actualizar las observaciones (grades) si son diferentes
		if (!re.getGrades().equals(recipeOld.getGrades())) {
			recipeOld.setGrades(re.getGrades());
		}

		// Actualizar país si es diferente
		if (!re.getCountry().equalsIgnoreCase(recipeOld.getCountry())) {
			recipeOld.setCountry(re.getCountry());
		}

		// Actualizar ciudad si es diferente
		if (!re.getCity().equalsIgnoreCase(recipeOld.getCity())) {
			recipeOld.setCity(re.getCity());
		}
		// Actualizar la fecha de actualización
		recipeOld.setUpdateDate(LocalDate.now());
		// Guardar la receta actualizada en el repositorio
		recipeRepository.save(recipeConverter.transform(recipeOld));
		return true;
	}

	// No interesa borrar recetas
	@Override
	public boolean deleteRecipe(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<recipeModel> getListRecipe() {
		// TODO Auto-generated method stub
		List<recipeModel> ListRecipeModel = new ArrayList<>();
		for (recipe r : recipeRepository.findAll()) {
			ListRecipeModel.add(recipeConverter.transform(r));
		}
		return ListRecipeModel;
	}

	@Override
	public recipeModel getRecipeById(int id) {
		// TODO Auto-generated method stub
		return recipeConverter.transform(recipeRepository.findById(id));
	}

	@Override
	public List<recipeModel> getListFindByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<recipeModel> getListFindByDificulty(String dificulty) {
		// TODO Auto-generated method stub
		List<recipeModel> m = new ArrayList<>();
		for (recipe r : recipeRepository.findByDifficulty(dificulty)) {
			m.add(recipeConverter.transform(r));
		}
		return m;
	}

	@Override
	public List<recipeModel> getListFindByLevel(String level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean toggleFav(Integer recipeId, int cookId) {
		// Obtener la entidad Cook desde el repositorio
		cook c = cookRepository.findById(cookId).orElseThrow(() -> new EntityNotFoundException("Cook not found"));
		// Obtener la entidad Recipe desde el repositorio
		recipe r = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
		// Comprobar si la receta ya está en la lista de favoritos
		if (c.getListRecipesFavorites().contains(r)) {
			// Si ya está, la quitamos
			c.getListRecipesFavorites().remove(r);
		} else {
			// Si no está, la agregamos
			c.getListRecipesFavorites().add(r);
		}
		// Guardar el cocinero actualizado en el repositorio
		cookRepository.save(c);
		return true; // O puedes devolver si se agregó o se quitó, según prefieras
	}

	@Override
	public List<recipeModel> filterRecipes(String category, String difficulty, Integer rating) {
		// TODO Auto-generated method stub
		List<recipeModel> l = new ArrayList<>();
		for(recipe r: recipeRepository.findRecipesByFilters(difficulty, rating)) {
			l.add(recipeConverter.transform(r));
			l=   l.stream()
				        .filter(recipe -> category == null || category.isEmpty() || recipe.getCategory().contains(category))
				        .collect(Collectors.toList());
		}
		return l;
	}

}
