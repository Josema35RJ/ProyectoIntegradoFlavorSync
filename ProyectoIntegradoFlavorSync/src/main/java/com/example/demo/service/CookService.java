package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;

public interface CookService {

	boolean deletedCook (int id);
	boolean updateCook (cookModel cook, List<String> l);
	List<cookModel> getAllCooks();
	List<cookModel> getFindByCooksChefs();
	List<cookModel> getFindByCooksProfessionals();
	List<cookModel> getFindByCooksAprendiz();
	List<cookModel> getFindByCooksAmateurs();
	boolean existeUsername(String username);
	UserDetails loadUserByUsername(String email);
	cookModel findById(int id);
	cookModel findByUsername(String username);
	boolean registrar(cookModel cook, List<String> culinaryTechniquesIds);
}
