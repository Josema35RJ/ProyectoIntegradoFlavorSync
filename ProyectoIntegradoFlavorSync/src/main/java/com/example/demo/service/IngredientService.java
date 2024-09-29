package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ingredientModel;
import com.example.demo.model.recipeIngredientModel;

public interface IngredientService {

	boolean addIngredient (ingredientModel in);
	boolean updateIngredient (ingredientModel in);
	boolean deleteIngredient (int id);
	List<ingredientModel> getListIngredients();
	ingredientModel findById(int i);
}
