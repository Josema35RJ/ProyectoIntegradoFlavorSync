package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.cook;

@Repository("cookRepository")
public interface CookRepository extends JpaRepository<cook, Serializable> {

	cook findByCookId(int CookId);
	List<cook> findByRol (String rol);
	List<cook> findByAge (int age); //Para ayudar a cocineros jovenes
	cook findByUsername(String username);

}