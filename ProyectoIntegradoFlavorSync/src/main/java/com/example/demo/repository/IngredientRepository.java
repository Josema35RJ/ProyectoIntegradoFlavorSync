package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.comment;
import com.example.demo.entity.cook;
import com.example.demo.entity.ingredient;

@Repository("ingredientRepository")
public interface IngredientRepository extends JpaRepository<ingredient, Serializable> {


}