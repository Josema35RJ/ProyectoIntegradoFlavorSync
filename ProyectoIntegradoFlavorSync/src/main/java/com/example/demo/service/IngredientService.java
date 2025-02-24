package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ingredientModel;

public interface IngredientService {

	boolean addIngredient (ingredientModel ingredient);
	boolean updateIngredient (ingredientModel ingredient);
	boolean deleteCulinaryTechniques (int id);
	List<ingredientModel> getListIngredients();
	ingredientModel findById(int id);
}
