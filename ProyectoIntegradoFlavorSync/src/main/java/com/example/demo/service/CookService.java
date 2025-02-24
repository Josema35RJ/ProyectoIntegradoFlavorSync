package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.cook;
import com.example.demo.model.cookModel;
import com.example.demo.model.recipeModel;

public interface CookService {

	boolean booleanCookCreate (cookModel c, recipeModel r);
	boolean deletedCook (int id);
	boolean updateCook (cookModel cook);
	List<cookModel> getAllCooks();
	List<cookModel> getFindByCooksChefs();
	List<cookModel> getFindByCooksProfessionals();
	List<cookModel> getFindByCooksAprendiz();
	List<cookModel> getFindByCooksAmateurs();
	List<cookModel> findUnverifiedCooks(LocalDateTime currentDateTime);
	void verifyUserEmail(String email);
	boolean existeUsername(String username);
	void verifyPunctuation (cookModel c);
	UserDetails loadUserByUsername(String email);
	cookModel findById(int id);
	cookModel findByUsername(String username);
	boolean registrar(cookModel cook, List<String> culinaryTechniquesIds);
	boolean updatePassword(String newP, cookModel cook);
	cookModel findByRecipeId(recipeModel r);
	cookModel registrar(cookModel cook);
	cookModel findByUsernameAndPassword(String username, String password);
	boolean booleanFav(cookModel c, recipeModel r);
	boolean updateCook(cookModel cook, List<String> culinaryTechniquesIds);
	boolean deleteRecipeByListCook(int id, Integer id2);
	boolean deleteCookAndRecipes(int id);
}
