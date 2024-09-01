package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.converter.CookConverter;
import com.example.demo.entity.cook;
import com.example.demo.model.cookModel;
import com.example.demo.repository.CookRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.CookService;

@Service("cookService")
public class CookServiceImpl implements UserDetailsService, CookService {

	@Autowired
	@Qualifier("cookRepository")
	private CookRepository cookRepository;

	@Autowired
	@Qualifier("cookConverter")
	private CookConverter cookConverter;

	@Override
	public boolean deletedCook(int id) {
		// TODO Auto-generated method stub
		cookModel c = cookConverter.transform(cookRepository.findByid(id));
		c.setEnabled(false);
		cookRepository.save(cookConverter.transform(c));
		return true;
	}

	@Override
	public boolean updateCook(cookModel cook) {
		// TODO Auto-generated method stub
		cookModel c = cookConverter.transform(cookRepository.findById(cook.getId()).get());
		if (!cook.getFirstName().equals(c.getFirstName())) {
			c.setFirstName(cook.getFirstName());
		} else if (cook.getLastName().equals(c.getLastName())) {
			c.setLastName(cook.getLastName());
		} else if (cook.getUsername().equals(c.getUsername())) {
			c.setUsername(cook.getUsername());
		} else if (cook.getListSpecialty().equals(c.getListSpecialty())) {
			c.setListSpecialty(cook.getListSpecialty());
		} else if (cook.getAge() == c.getAge()) {
			c.setAge(cook.getAge());
		}
		return true;
	}

	@Override
	public List<cookModel> getAllCooks() {
		// TODO Auto-generated method stub
		List<cookModel> ListModel = new ArrayList<>();
		for (cook c : cookRepository.findAll()) {

			ListModel.add(cookConverter.transform(c));
		}
		return ListModel;
	}

	@Override
	public List<cookModel> getFindByCooksChefs() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksChefs = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Chef".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksChefs.add(cookConverter.transform(c));
		}
		return ListModelCooksChefs;

	}

	@Override
	public List<cookModel> getFindByCooksProfessionals() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksProfesionals = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Profesional".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksProfesionals.add(cookConverter.transform(c));
		}
		return ListModelCooksProfesionals;

	}

	@Override
	public List<cookModel> getFindByCooksAprendiz() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksAprendiz = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Aprendiz".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksAprendiz.add(cookConverter.transform(c));
		}
		return ListModelCooksAprendiz;
	}

	@Override
	public List<cookModel> getFindByCooksAmateurs() {
		// TODO Auto-generated method stub
		List<cookModel> ListModelCooksAmateurs = new ArrayList<>();
		for (cook c : cookRepository.findAll().stream().filter(c -> "Amateurs".equals(c.getRole()))
				.collect(Collectors.toList())) // Recoger en una lista) {
		{
			ListModelCooksAmateurs.add(cookConverter.transform(c));
		}
		return ListModelCooksAmateurs;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		CustomUserDetails builder = null;
		cook user = cookRepository.findByUsername(email);

		if (user != null) {
			if (user.isEnabled()) {
				builder = new CustomUserDetails(user.getFirstName(), user.getPassword(),
						Collections.singletonList(new SimpleGrantedAuthority(user.getRole())), user.getId());
			} else {
				throw new DisabledException("El cocinero no está activado");
			}
		} else {
			throw new UsernameNotFoundException("Cocinero no encontrado con el email: " + email);
		}

		return builder;
	}
	
	@Override
	public boolean existeUsername(String username) {
		// TODO Auto-generated method stub
		if (cookRepository.findByUsername(username) == null)
			return false;
		return true;
	}

	@Override
	public void registrar(cookModel cook) {
		// TODO Auto-generated method stub
		cook.setPassword(passwordEncoder().encode(cook.getPassword()));
		cook.setEnabled(true);
		if (cook.getRole() == null)
			cook.setRol("ROL_COOKAPRENDIZ");
		cookRepository.save(cookConverter.transform(cook));
	}

}
