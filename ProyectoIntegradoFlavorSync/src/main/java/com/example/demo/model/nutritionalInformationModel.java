package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class nutritionalInformationModel {


    private Integer id;

    // Calorías por porción
    private float calories;

    // Grasas en gramos
    private float fat;

    // Carbohidratos en gramos
    private float carbohydrates;

    // Proteínas en gramos
    private float proteins;

    // Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
    private String otherNutrients;
}

