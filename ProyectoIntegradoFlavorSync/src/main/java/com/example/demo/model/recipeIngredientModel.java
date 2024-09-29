package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class recipeIngredientModel {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private recipeModel recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private ingredientModel ingredient;

    @Column(nullable = false)
    private float cant;  // Cantidad del ingrediente

    private String unit;  // Unidad de medida (gramos, cucharadas, etc.)

	public recipeIngredientModel() {
		super();
	}

	public recipeIngredientModel(Integer id, recipeModel recipe,
			ingredientModel ingredient, float cant, String unit) {
		super();
		this.id = id;
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.cant = cant;
		this.unit = unit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public recipeModel getRecipe() {
		return recipe;
	}

	public void setRecipe(recipeModel recipe) {
		this.recipe = recipe;
	}

	public ingredientModel getIngredient() {
		return ingredient;
	}

	public void setIngredient(ingredientModel ingredient) {
		this.ingredient = ingredient;
	}

	public float getCant() {
		return cant;
	}

	public void setCant(float cant) {
		this.cant = cant;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "recipeIngredientModel [id=" + id + ", recipe=" + recipe + ", ingredient=" + ingredient + ", cant="
				+ cant + ", unit=" + unit + "]";
	}
}
