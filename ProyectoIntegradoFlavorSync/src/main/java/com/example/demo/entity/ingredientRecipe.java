package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ingredientRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "receta_id", nullable = false)
	private recipe receta;

	@ManyToOne
	@JoinColumn(name = "ingrediente_id", nullable = false)
	private ingredient ingrediente;

	@Column(nullable = false)
	private Double cantidad; // Cantidad del ingrediente en la receta

	@Column(nullable = false, length = 50)
	private String unidadMedida; // Unidad de medida, por ejemplo, "gramos", "ml", "taza", etc.

	// Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public recipe getReceta() {
		return receta;
	}

	public void setReceta(recipe receta) {
		this.receta = receta;
	}

	public ingredient getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(ingredient ingrediente) {
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
}
