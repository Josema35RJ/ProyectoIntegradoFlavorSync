package com.example.demo.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.recipe;
import com.example.demo.model.recipeModel;

@Repository("recipeRepository")
public interface RecipeRepository extends JpaRepository<recipe, Serializable> {

	recipe findById(int RecipeId);

	List<recipe> findByLevel(String level);

	List<recipe> findByPreparationTime(double preparationTime);

	List<recipe> findByWhereItisDone(List<String> whereItisDone);

	List<recipe> findByCategory(List<String> category);

	List<recipe> findByAverageRating(float averageRating);

	List<recipe> findByDifficulty(String difficulty);

	List<recipe> findBylistkitchenUtensils(List<String> listkitchenUtensils);

	@Query("SELECT r FROM recipe r WHERE " +
		       "(:difficulty IS NULL OR :difficulty = '' OR r.difficulty = :difficulty) AND " +
		       "(:rating IS NULL OR :rating = 0 OR r.averageRating >= :rating)")
		List<recipe> findRecipesByFilters(@Param("difficulty") String difficulty, 
		                                  @Param("rating") Integer rating);


}