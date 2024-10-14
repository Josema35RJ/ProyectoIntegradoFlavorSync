package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CookConverter;
import com.example.demo.converter.RecipeConverter;
import com.example.demo.entity.recipe;
import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;
import com.example.demo.repository.CookRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.service.RecipeService;

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
        System.out.println("/*******************************************");
        System.out.println(recipe);
	    // Luego agregarla a la lista de recetas del cocinero y guardar la entidad cook
	    cook.getListRecipes().add(recipe);
	    cookRepository.save(cookConverter.transform(cook));

	    return true;
	}
	@Override
	public boolean updateRecipe(recipeModel re) {
		// TODO Auto-generated method stub
		recipeModel r = recipeConverter.transform(recipeRepository.findById(re.getId()).get());
		if (!re.getName().equalsIgnoreCase(r.getName())) {
			r.setName(re.getName());
		} else if (re.getPreparationTime() != (r.getPreparationTime())) {
			r.setPreparationTime(re.getPreparationTime());
		} else if (re.getDifficulty().equalsIgnoreCase(r.getDifficulty())) {
			r.setDifficulty(re.getDifficulty());
		} else if (re.getCategory().toString().equalsIgnoreCase(r.getCategory().toString())) {
			r.setCategory(re.getCategory());
		} else if (re.getDiners() != r.getDiners()) {
			r.setDiners(re.getDiners());
		}
		r.setUpdateDate(LocalDate.now());
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
	public List<recipeModel> getListFindByPostreRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<recipeModel> getListFindByEntrantsRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<recipeModel> getListFindByFacilRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<recipeModel> getListFindByDificilRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<recipeModel> getListFindByMedioRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<recipeModel> getListFindByExpertoRecipe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public recipeModel getRecipeById(int id) {
		// TODO Auto-generated method stub
		return recipeConverter.transform(recipeRepository.findById(id));
	}

}
