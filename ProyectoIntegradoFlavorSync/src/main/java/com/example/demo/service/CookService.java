package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.cookModel;

public interface CookService {

	boolean deletedCook (int id);
	boolean updateCook (cookModel cook);
	List<cookModel> getAllCooks();
	List<cookModel> getFindByCooksChefs();
	List<cookModel> getFindByCooksProfessionals();
	List<cookModel> getFindByCooksAprendiz();
	List<cookModel> getFindByCooksAmateurs();
	boolean existeUsername(String username);
	void registrar(cookModel cook);
	UserDetails loadUserByUsername(String email);
}
