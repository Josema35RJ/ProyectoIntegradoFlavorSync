package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ingredient;
import com.example.demo.entity.recipe;

@Repository("recipeRepository")
public interface RecipeRepository extends JpaRepository<recipe, Serializable> {

	recipe findById(int RecipeId);
    List<recipe> findBylistIngredients (List<ingredient> ingredientsModel);
    List<recipe> findByLevel (String level);
    List<recipe> findByPreparationTime (double preparationTime);
    List<recipe> findByWhereItisDone (List<String> whereItisDone);
    List<recipe> findByCategory (String category);
    List<recipe> findByAverageRating (float averageRating);
    List<recipe> findByDifficulty (String difficulty);
    List<recipe> findBylistkitchenUtensils (List<String> listkitchenUtensils);
    
}