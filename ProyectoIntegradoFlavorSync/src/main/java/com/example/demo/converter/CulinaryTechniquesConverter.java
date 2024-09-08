package com.example.demo.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.example.demo.entity.comment;
import com.example.demo.entity.culinaryTechniques;
import com.example.demo.model.commentModel;
import com.example.demo.model.culinaryTechniquesModel;

@Component("culinaryTechniquesConverter")
public class CulinaryTechniquesConverter {

	public culinaryTechniques transform(culinaryTechniquesModel co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, culinaryTechniques.class);
	}

	public culinaryTechniquesModel transform(culinaryTechniques co) {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(co, culinaryTechniquesModel.class);
	}

}
