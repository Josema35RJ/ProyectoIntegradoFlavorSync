package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.recipeIngredient;
import com.example.demo.model.recipeIngredientModel;

@Component("recipeIngredientConverter")
public class RecipeIngredientConverter {

	public recipeIngredient transform(recipeIngredientModel co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, recipeIngredient.class);
	}

	public recipeIngredientModel transform(recipeIngredient co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, recipeIngredientModel.class);
	}

}
