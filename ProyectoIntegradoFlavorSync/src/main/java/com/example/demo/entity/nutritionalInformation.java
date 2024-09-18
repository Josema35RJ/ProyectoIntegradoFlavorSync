package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class nutritionalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // Calorías por porción
    @Positive(message = "Calories must be a positive number")
    private float calories;

    // Grasas en gramos
    @Positive(message = "Fat content must be a positive number")
    private float fat;

    // Carbohidratos en gramos
    @Positive(message = "Carbohydrates content must be a positive number")
    private float carbohydrates;

    // Proteínas en gramos
    @Positive(message = "Proteins content must be a positive number")
    private float proteins;

    // Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
    private String otherNutrients;
}
