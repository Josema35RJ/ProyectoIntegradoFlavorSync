package com.example.demo.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class ingredientModel {

	// Identificador único para cada comentario.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	//Nombre del ingrediente
	@Column(name = "name", nullable = false)
	private String name;

	//Cantidad del ingrediente
	@NotNull
	@Positive(message = "The cant must be a positive number")
	private float cant ;

	//En se mide la cantidad, gramos, cucharadas...
	@NotNull
	private String unit;

	public ingredientModel(String name, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
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
		return "ingredientModel [id=" + id + ", name=" + name + ", cant=" + cant + ", unit=" + unit + "]";
	}
}
