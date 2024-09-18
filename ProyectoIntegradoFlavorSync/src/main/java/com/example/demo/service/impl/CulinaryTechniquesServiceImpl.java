package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.demo.converter.CulinaryTechniquesConverter;
import com.example.demo.entity.culinaryTechniques;
import com.example.demo.model.culinaryTechniquesModel;
import com.example.demo.repository.CulinaryTechniquesRepository;
import com.example.demo.service.CulinaryTechniquesService;

@Service("culinaryTechniquesService")
public class CulinaryTechniquesServiceImpl implements CulinaryTechniquesService {
	  
	  @Autowired
	    @Qualifier("culinaryTechniquesConverter")
	    private CulinaryTechniquesConverter culinaryTechniquesConverter;
	  
	  @Autowired
	    @Qualifier("culinaryTechniquesRepository")
	    private CulinaryTechniquesRepository culinaryTechniquesRepository;

	@Override
	public boolean addCulinaryTechniques(culinaryTechniquesModel culinaryTechniques) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCulinaryTechniques(culinaryTechniquesModel culinaryTechniques) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCulinaryTechniques(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<culinaryTechniquesModel> getListCulinaryTechniques() {
		// TODO Auto-generated method stub
		List<culinaryTechniquesModel> l= new ArrayList<>();
		for(culinaryTechniques ct: culinaryTechniquesRepository.findAll()) {
			l.add(culinaryTechniquesConverter.transform(ct));
		}
		return l;
	}

	@Override
	public culinaryTechniquesModel findById(int id) {
		// TODO Auto-generated method stub
		return culinaryTechniquesConverter.transform(culinaryTechniquesRepository.findById(id));
	}
	  


}
