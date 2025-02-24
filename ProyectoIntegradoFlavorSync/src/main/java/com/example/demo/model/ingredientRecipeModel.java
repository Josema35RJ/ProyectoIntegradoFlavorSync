package com.example.demo.model;

public class ingredientRecipeModel {

	private Long id;

	private recipeModel receta;

	private ingredientModel ingrediente;

	private Double cantidad; // Cantidad del ingrediente en la receta

	private String unidadMedida; // Unidad de medida, por ejemplo, "gramos", "ml", "taza", etc.

	public ingredientRecipeModel() {
		super();
	}

	public ingredientRecipeModel(recipeModel receta, ingredientModel ingrediente, Double cantidad,
			String unidadMedida) {
		super();
		this.receta = receta;
		this.ingrediente = ingrediente;
		this.cantidad = cantidad;
		this.unidadMedida = unidadMedida;
	}

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public recipeModel getReceta() {
		return receta;
	}

	public void setReceta(recipeModel receta) {
		this.receta = receta;
	}

	public ingredientModel getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(ingredientModel ingrediente) {
		this.ingrediente = ingrediente;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	@Override
	public String toString() {
		return "ingredientRecipeModel [ingrediente=" + ingrediente.getNombre() + ", cantidad="
				+ cantidad + ", unidadMedida=" + unidadMedida + "]";
	}
	
}
