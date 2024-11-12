package com.example.demo.repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.cook;
import com.example.demo.entity.recipe;
import com.example.demo.model.cookModel;

@Repository("cookRepository")
public interface CookRepository extends JpaRepository<cook, Serializable> {

	cook findByid(int CookId);

	List<cook> findByRole(String rol);

	List<cook> findByBirthDate(LocalDate BirthDate); // Para ayudar a cocineros jovenes

	cook findByUsername(String username);

	void deleteByUsername(String username);

	  @Query("SELECT c FROM cook c JOIN c.listRecipes r WHERE r = :recipe")
	    cook findByRecipe(@Param("recipe") recipe recipe);
}