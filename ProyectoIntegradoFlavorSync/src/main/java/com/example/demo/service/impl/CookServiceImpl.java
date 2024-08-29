package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CookConverter;
import com.example.demo.model.cookModel;
import com.example.demo.repository.CookRepository;
import com.example.demo.service.CookService;

@Service("cookService")
public class CookServiceImpl implements CookService {

	  @Autowired
	    @Qualifier("cookRepository")
	    private CookRepository cookRepository;
	  
	  @Autowired
	    @Qualifier("cookConverter")
	    private CookConverter cookConverter;

	@Override
	public boolean addCook(cookModel cook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletedCook(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCook(cookModel cook) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<cookModel> getAllCooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cookModel> getFindByCooksChefs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cookModel> getFindByCooksProfessionals() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cookModel> getFindByCooksAprendiz() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<cookModel> getFindByCooksAmateurs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existeUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void registrar(cookModel cook) {
		// TODO Auto-generated method stub
		
	}

	
}
