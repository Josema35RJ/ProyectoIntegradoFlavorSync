package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.IngredientConverter;
import com.example.demo.model.cookModel;
import com.example.demo.model.ingredientModel;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;

@Service("ingredientService")
public class IngredientServiceImpl implements IngredientService {

	  @Autowired
	    @Qualifier("ingredientRepository")
	    private IngredientRepository ingredientRepository;
	  
	  @Autowired
	    @Qualifier("ingredientConverter")
	    private IngredientConverter ingredientConverter;

	@Override
	public boolean addIngredient(ingredientModel in) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateIngredient(ingredientModel in) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteIngredient(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ingredientModel> getListIngredients() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
