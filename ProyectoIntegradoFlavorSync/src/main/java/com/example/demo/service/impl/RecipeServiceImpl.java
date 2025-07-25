package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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
	public boolean updateRecipe(recipeModel re) {
	    // Buscar la receta existente en el repositorio
	    recipeModel recipeOld = recipeConverter.transform(recipeRepository.findById(re.getId()).get());

	    // Actualizar nombre si es diferente y no está vacío o nulo
	    if (re.getName() != null && !re.getName().isEmpty() && !re.getName().equalsIgnoreCase(recipeOld.getName())) {
	        recipeOld.setName(re.getName());
	    }

	    // Actualizar cantidad de comensales si es diferente
	    if (re.getDiners() > 0 && re.getDiners() != recipeOld.getDiners()) {
	        recipeOld.setDiners(re.getDiners());
	    }

	    // Actualizar tiempo de preparación si es diferente
	    if (re.getPreparationTime() > 0 && re.getPreparationTime() != recipeOld.getPreparationTime()) {
	        recipeOld.setPreparationTime(re.getPreparationTime());
	    }

	    // Actualizar lugar de elaboración si es diferente y no está vacío o nulo
	    if (re.getWhereItisDone() != null && !re.getWhereItisDone().isEmpty() && !re.getWhereItisDone().equals(recipeOld.getWhereItisDone())) {
	        recipeOld.setWhereItisDone(re.getWhereItisDone());
	    }

	    // Actualizar categorías si son diferentes y no están vacías o nulas
	    if (re.getCategory() != null && !re.getCategory().isEmpty() && !re.getCategory().equals(recipeOld.getCategory())) {
	        recipeOld.setCategory(re.getCategory());
	    }

	    // Actualizar dificultad si es diferente y no está vacío o nulo
	    if (re.getDifficulty() != null && !re.getDifficulty().isEmpty() && !re.getDifficulty().equalsIgnoreCase(recipeOld.getDifficulty())) {
	        recipeOld.setDifficulty(re.getDifficulty());
	    }

	    // Actualizar las técnicas culinarias si son diferentes y no están vacías o nulas
	    if (re.getListRecipeTechniques() != null && !re.getListRecipeTechniques().equals(recipeOld.getListRecipeTechniques())) {
	        recipeOld.setListRecipeTechniques(re.getListRecipeTechniques());
	    }

	    // Actualizar utensilios de cocina si son diferentes y no están vacíos o nulos
	    if (re.getListkitchenUtensils() != null && !re.getListkitchenUtensils().equals(recipeOld.getListkitchenUtensils())) {
	        recipeOld.setListkitchenUtensils(re.getListkitchenUtensils());
	    }

	    // Actualizar restricciones alimentarias si son diferentes y no están vacías o nulas
	    if (re.getAllergensAndDietaryRestrictions() != null && !re.getAllergensAndDietaryRestrictions().equals(recipeOld.getAllergensAndDietaryRestrictions())) {
	        recipeOld.setAllergensAndDietaryRestrictions(re.getAllergensAndDietaryRestrictions());
	    }

	    // Actualizar la información nutricional si es diferente y no es nula
	    if (re.getNutritionalInformation() != null && !re.getNutritionalInformation().equals(recipeOld.getNutritionalInformation())) {
	        recipeOld.setNutritionalInformation(re.getNutritionalInformation());
	    }

	    // Actualizar las instrucciones si son diferentes y no están vacías o nulas
	    if (re.getInstructions() != null && !re.getInstructions().isEmpty() && !re.getInstructions().equals(recipeOld.getInstructions())) {
	        recipeOld.setInstructions(re.getInstructions());
	    }

	    // Actualizar los ingredientes si son diferentes y no están vacíos o nulos
	    if (re.getIngredients() != null && !re.getIngredients().isEmpty() && !re.getIngredients().equals(recipeOld.getIngredients())) {
	        recipeOld.setIngredients(re.getIngredients());
	    }

	    // Actualizar las imágenes si son diferentes y no están vacías o nulas
	    if (re.getImagesRecipe() != null && !re.getImagesRecipe().equals(recipeOld.getImagesRecipe())) {
	        recipeOld.setImagesRecipe(re.getImagesRecipe());
	    }

	    // Actualizar la imagen de perfil si es diferente y no es nula
	    if (re.getImageRecipePerfil() != null && !re.getImageRecipePerfil().equals(recipeOld.getImageRecipePerfil())) {
	        recipeOld.setImageRecipePerfil(re.getImageRecipePerfil());
	    }

	    // Actualizar el historial si es diferente y no está vacío o nulo
	    if (re.getHistory() != null && !re.getHistory().isEmpty() && !re.getHistory().equals(recipeOld.getHistory())) {
	        recipeOld.setHistory(re.getHistory());
	    }

	    // Actualizar las observaciones (grades) si son diferentes y no están vacías o nulas
	    if (re.getGrades() != null && !re.getGrades().equals(recipeOld.getGrades())) {
	        recipeOld.setGrades(re.getGrades());
	    }

	    // Actualizar país si es diferente y no está vacío o nulo
	    if (re.getCountry() != null && !re.getCountry().isEmpty() && !re.getCountry().equalsIgnoreCase(recipeOld.getCountry())) {
	        recipeOld.setCountry(re.getCountry());
	    }

	    // Actualizar ciudad si es diferente y no está vacío o nulo
	    if (re.getCity() != null && !re.getCity().isEmpty() && !re.getCity().equalsIgnoreCase(recipeOld.getCity())) {
	        recipeOld.setCity(re.getCity());
	    }

	    // Actualizar la fecha de actualización
	    recipeOld.setUpdateDate(LocalDate.now());

	    // Guardar la receta actualizada en el repositorio
	    recipeRepository.save(recipeConverter.transform(recipeOld));
	    return true;
	}


	@Override
	public boolean updateRecipe(int id, recipeModel re, String imagenPerfilRecipe, String[] ImagesBase64) {
		// Buscar la receta existente en el repositorio
		recipeModel recipeOld = recipeConverter.transform(recipeRepository.findById(id));

		if (recipeOld.toString().isEmpty()) {
			// Si no se encuentra la receta, retornar false
			return false;
		}
		if (imagenPerfilRecipe.getBytes().length > 0) {
			recipeOld.setImageRecipePerfil(imagenPerfilRecipe);
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
		if (!re.getIngredients().isEmpty()) {
			recipeOld.setIngredients(re.getIngredients());
		}

		// Actualizar las imágenes si son diferentes
		if (!re.getImagesRecipe().equals(recipeOld.getImagesRecipe())) {
			recipeOld.setImagesRecipe(re.getImagesRecipe());
		}

		// Actualizar la imagen de perfil si es diferente
		if (re.getImageRecipePerfil() != null
				&& !re.getImageRecipePerfil().equals(recipeOld.getImageRecipePerfil())) {
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

		if (ImagesBase64.length > 0) {
			recipeOld.getImagesRecipe().clear();
			for (String x : ImagesBase64) {
				recipeOld.getImagesRecipe().add(x);
			}

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
		recipe r = recipeRepository.findById(id);
		if(r.getImagesRecipe().isEmpty())
			r.getImagesRecipe().clear();
		recipeRepository.delete(recipeRepository.findById(id));
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
	public List<recipeModel> filterRecipes(String ingredients, String category, String difficulty, Integer rating) {
		List<recipeModel> filteredRecipes = new ArrayList<>();

		// Convertimos la cadena de ingredientes en una lista para facilitar la
		// comparación
		List<String> ingredientList = (ingredients == null || ingredients.isEmpty()) ? new ArrayList<>()
				: Arrays.asList(ingredients.split(",")).stream().map(String::trim) // Limpiamos espacios en blanco
						.collect(Collectors.toList());

		// Obtenemos las recetas según la dificultad y la calificación
		for (recipe r : recipeRepository.findRecipesByFilters(difficulty, rating)) {
			recipeModel recipeModel = recipeConverter.transform(r);
			filteredRecipes.add(recipeModel);
		}

		// Aplicamos filtros para categoría e ingredientes
		

		return filteredRecipes;
	}

	@Override
	public boolean toggleLike(Integer recipeId, int cookId) {
	    // Obtener al cocinero por su ID
		boolean isLiked = false;
	    cook cook = cookRepository.findById(cookId).orElseThrow(() -> new RuntimeException("Cook not found"));

	    // Obtener la receta por su ID
	    recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));

	   for(recipe r : cook.getLikedRecipes())
		   if(r.getId()==recipe.getId())
			   isLiked=true;

	    if (isLiked) {
	        // Si ya dio "like", eliminarlo
	        recipe.setLikesCount(recipe.getLikesCount() - 1);
	        // Usar un Iterator para eliminar todas las recetas del Set "likedRecipes"
	        Iterator<recipe> iterator = cook.getLikedRecipes().iterator();
	        while (iterator.hasNext()) {
	            recipe likedRecipe = iterator.next();
	            if (likedRecipe.getId()==recipe.getId()) {
	                iterator.remove();  // Eliminar la receta del Set utilizando el Iterator
	            }
	        }
	    } else {
	        // Si no dio "like", agregarlo
	        recipe.setLikesCount(recipe.getLikesCount() + 1);
	        cook.getLikedRecipes().add(recipe);  // Añadir la receta al Set
	    }

	    // Guardar los cambios en ambas entidades
	    recipeRepository.save(recipe);
	    cookRepository.save(cook);

	    return !isLiked; // Devuelve el nuevo estado del "like"
	}


	@Override
	public boolean booleanLike(cookModel c, recipeModel r) {
		// TODO Auto-generated method stub
		boolean isLiked = false;
		 for(recipeModel re : c.getLikedRecipes())
			   if(re.getId()==r.getId())
				   isLiked=true;
		return isLiked;
	}
}
