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
	    @Qualifier("ingredientRepository")
	    private IngredientRepository ingredientRepository;
	  
	  @Autowired
	    @Qualifier("ingredientConverter")
	    private IngredientConverter ingredientConverter;

	@Override
	public boolean addIngredient(ingredientModel in) {
		// TODO Auto-generated method stub
		ingredientRepository.save(ingredientConverter.transform(in));
		return true;
	}

	@Override
	public boolean updateIngredient(ingredientModel in) {
		// TODO Auto-generated method 
        ingredientModel i = ingredientConverter.transform(ingredientRepository.findById(in.getId()).get());
		if(i.getName().equalsIgnoreCase(in.getName())) {
			 i.setName(in.getName());
		}else if (i.getUnit().equals(in.getUnit())){
			  i.setUnit(in.getUnit());
		}else if (i.getCant()==in.getCant()) {
			  i.setCant(in.getCant());
		}
		return true;
	}

	@Override
	public boolean deleteIngredient(int id) {
		// TODO Auto-generated method stub
		ingredientRepository.delete(ingredientRepository.findById(id).get());
		return true;
	}

	@Override
	public List<ingredientModel> getListIngredients() {
		// TODO Auto-generated method stub
		List<ingredientModel> ListIngredientModel = new ArrayList<>();
		for(ingredient i : ingredientRepository.findAll()) {
			ListIngredientModel.add(ingredientConverter.transform(i));
		}
		return ListIngredientModel;
	}

	
}
