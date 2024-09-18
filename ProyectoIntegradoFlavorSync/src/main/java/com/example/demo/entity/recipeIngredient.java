package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "recipeIngredient")
@Data
public class recipeIngredient {

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

}
