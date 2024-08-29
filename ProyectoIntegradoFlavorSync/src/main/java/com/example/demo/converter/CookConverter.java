package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.cook;
import com.example.demo.model.cookModel;

@Component("cookConverter")
public class CookConverter {

	public cook transform(cookModel co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, cook.class);
	}

	public cookModel transform(cook co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, cookModel.class);
	}

}