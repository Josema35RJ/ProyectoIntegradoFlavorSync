package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ingredient;
import com.example.demo.model.ingredientModel;

@Repository("ingredientRepository")
public interface IngredientRepository extends JpaRepository<ingredient, Serializable> {

	ingredient findById(int ingredientId);

}