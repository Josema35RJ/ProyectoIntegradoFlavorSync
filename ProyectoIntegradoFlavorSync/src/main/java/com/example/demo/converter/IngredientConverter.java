package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ingredient;
import com.example.demo.model.ingredientModel;

@Component("ingredientConverter")
public class IngredientConverter {

	public ingredient transform(ingredientModel co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, ingredient.class);
	}

	public ingredientModel transform(ingredient co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, ingredientModel.class);
	}

}