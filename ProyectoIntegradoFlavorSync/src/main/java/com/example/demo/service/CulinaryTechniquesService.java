package com.example.demo.service;

import java.util.List;

import com.example.demo.model.commentModel;
import com.example.demo.model.culinaryTechniquesModel;

public interface CulinaryTechniquesService {
	
	boolean addCulinaryTechniques (culinaryTechniquesModel comment);
	boolean updateCulinaryTechniques (culinaryTechniquesModel comment);
	boolean deleteCulinaryTechniques (int id);
	List<culinaryTechniquesModel> getListCulinaryTechniques();
}
