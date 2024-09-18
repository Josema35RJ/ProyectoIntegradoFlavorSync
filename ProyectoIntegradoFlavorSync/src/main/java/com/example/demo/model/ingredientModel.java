package com.example.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ingredientModel {

	// Identificador único para cada comentario.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	//Nombre del ingrediente
	@Column(name = "name", nullable = false)
	private String name;
	
	// Relación many-to-many usando la clase RecipeIngredient como entidad intermedia
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<recipeIngredientModel> recipes;

	public ingredientModel() {
		super();
	}

	public ingredientModel(Integer id, String name,
			@NotNull @Positive(message = "The cant must be a positive number") float cant, @NotNull String unit,
			List<recipeIngredientModel> recipes) {
		super();
		this.id = id;
		this.recipes = recipes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<recipeIngredientModel> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<recipeIngredientModel> recipes) {
		this.recipes = recipes;
	}

	@Override
	public String toString() {
		return "ingredientModel [id=" + id + ", name=" + name +  "]";
	}
}
