package com.example.demo.service;

import java.util.List;

import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;

public interface RecipeService {

	boolean addRecipe (recipeModel re, cookModel c);
	boolean updateRecipe (recipeModel re);
	boolean deleteRecipe (int id);
	List<recipeModel> getListRecipe();
	List<recipeModel> getListFindByPostreRecipe();
	List<recipeModel> getListFindByEntrantsRecipe();
	List<recipeModel> getListFindByFacilRecipe();
	List<recipeModel> getListFindByDificilRecipe();
	List<recipeModel> getListFindByMedioRecipe();
	List<recipeModel> getListFindByExpertoRecipe();
	recipeModel getRecipeById(int id);
}
