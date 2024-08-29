package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.recipe;
import com.example.demo.model.recipeModel;

@Component("recipeConverter")
public class RecipeConverter {

	public recipe transform(recipeModel re) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(re, recipe.class);
	}

	public recipeModel transform(recipe re) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(re, recipeModel.class);
	}

}