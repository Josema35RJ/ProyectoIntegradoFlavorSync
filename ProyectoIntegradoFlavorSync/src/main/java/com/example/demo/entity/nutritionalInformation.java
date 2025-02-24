package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name = "nutritionalInformation")
@Data
public class nutritionalInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Calorías por porción
	@Column(name = "calories")
	@Positive(message = "Calories must be a positive number")
	private Float calories;

	// Grasas en gramos
	@Column(name = "fat")
	@Positive(message = "Fat content must be a positive number")
	private Float fat;

	// Carbohidratos en gramos
	@Column(name = "carbohydrates")
	@Positive(message = "Carbohydrates content must be a positive number")
	private Float carbohydrates;

	// Proteínas en gramos
	@Column(name = "proteins")
	@Positive(message = "Proteins content must be a positive number")
	private Float proteins;

	// Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
	@Column(name = "otherNutrients")
	private String otherNutrients;
}
