package com.example.demo.service;

import java.util.List;

import com.example.demo.model.ingredientModel;

public interface IngredientService {

	boolean addIngredient (ingredientModel in);
	boolean updateIngredient (ingredientModel in);
	boolean deleteIngredient (int id);
	List<ingredientModel> getListIngredients();
}
