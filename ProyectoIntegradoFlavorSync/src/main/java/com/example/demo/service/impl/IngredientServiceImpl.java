package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.IngredientConverter;
import com.example.demo.entity.ingredient;
import com.example.demo.model.ingredientModel;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.service.IngredientService;

@Service("ingredientService")
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	@Qualifier("ingredientConverter")
	private IngredientConverter ingredientConverter;

	@Autowired
	@Qualifier("ingredientRepository")
	private IngredientRepository ingredientRepository;

	@Override
	public boolean addIngredient(ingredientModel ingredient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateIngredient(ingredientModel ingredient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCulinaryTechniques(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ingredientModel> getListIngredients() {
		// TODO Auto-generated method stub
		List<ingredientModel> n = new ArrayList<>();
		for (ingredient x : ingredientRepository.findAll()) {
			n.add(ingredientConverter.transform(x));
		}
		return n;
	}

	@Override
	public ingredientModel findById(int id) {
		// TODO Auto-generated method stub
		return ingredientConverter.transform(ingredientRepository.findById(id).get());
	}

}
