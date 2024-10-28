package com.example.demo.service;

import java.util.List;

import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;

public interface RecipeService {

	boolean addRecipe(recipeModel re, cookModel c);

	boolean updateRecipe(int id, recipeModel re);

	boolean deleteRecipe(int id);

	List<recipeModel> getListRecipe();

	List<recipeModel> getListFindByCategory(String category);

	List<recipeModel> getListFindByDificulty(String dificulty);

	List<recipeModel> getListFindByLevel(String level);

	recipeModel getRecipeById(int id);

	boolean toggleFav(Integer recipeId, int id);

	public List<recipeModel> filterRecipes(String category, String difficulty, Integer rating);
}
