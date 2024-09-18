package com.example.demo.model;

import com.example.demo.entity.ingredient;
import com.example.demo.entity.recipe;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

public class recipeIngredientModel {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private ingredient ingredient;

    @Column(nullable = false)
    private float cant;  // Cantidad del ingrediente

    private String unit;  // Unidad de medida (gramos, cucharadas, etc.)

	public recipeIngredientModel() {
		super();
	}

	public recipeIngredientModel(Integer id, com.example.demo.entity.recipe recipe,
			com.example.demo.entity.ingredient ingredient, float cant, String unit) {
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

	public recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(recipe recipe) {
		this.recipe = recipe;
	}

	public ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(ingredient ingredient) {
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
