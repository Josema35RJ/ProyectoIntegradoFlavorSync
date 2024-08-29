package com.example.demo.entity;

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

@Entity
@Table(name = "ingredient")
@Data
public class ingredient {

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
	private String unit ;
	
	public ingredient(String name, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.unit = unit;
	}
}
