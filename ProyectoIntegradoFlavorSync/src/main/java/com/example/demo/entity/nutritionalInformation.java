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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Calorías por porción
	@Column(name = "calories", nullable = false)
    @Positive(message = "Calories must be a positive number")
    private float calories;

    // Grasas en gramos
	@Column(name = "fat", nullable = false)
    @Positive(message = "Fat content must be a positive number")
    private float fat;

    // Carbohidratos en gramos
	@Column(name = "carbohydrates", nullable = false)
    @Positive(message = "Carbohydrates content must be a positive number")
    private float carbohydrates;

    // Proteínas en gramos
	@Column(name = "proteins", nullable = false)
    @Positive(message = "Proteins content must be a positive number")
    private float proteins;

    // Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
	@Column(name = "otherNutrients")
    private String otherNutrients;
}
