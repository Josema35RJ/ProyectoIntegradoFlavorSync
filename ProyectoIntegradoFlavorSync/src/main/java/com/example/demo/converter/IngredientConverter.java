package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ingredient;
import com.example.demo.model.ingredientModel;

@Component("ingredientConverter")
public class IngredientConverter {

	public ingredient transform(ingredientModel in) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(in, ingredient.class);
	}

	public ingredientModel transform(ingredient in) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(in, ingredientModel.class);
	}
}