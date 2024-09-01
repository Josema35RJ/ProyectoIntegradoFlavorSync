package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "recipe")
@Data
public class recipe {
	// Identificador único para cada receta.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Nombre de la receta
	@Column(name = "name", nullable = false)
	@Size(max = 25, message = "The name cannot exceed 25 characters")
	private String name;

	// Cocinero aprendiz, profesional, chef?
	private String level;

	// Para cuantas personas es la receta
	@Column(name = "diners", nullable = false)
	@Positive(message = "The diners must be a positive number")
	private int diners;

	// Cuanto se tarda en elaborar la receta, en minutos
	@Column(name = "preparationTime", nullable = false)
	@Positive(message = "The preparationTime must be a positive number")
	private float preparationTime;

	// Si la receta se hace, en horno, microondas
	private List<String> whereItisDone ;

	// Si la receta es postre, entrante, primer plato...
	private String category;

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	@Positive(message = "The punctuation must be a positive number")
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
	 @OneToMany( cascade = CascadeType.ALL)
	private List<comment> listComments;

	// lista de ingredientes necesarios
	 @OneToMany( cascade = CascadeType.ALL)
	@NotNull
	private List<ingredient> listIngredients ;
	 
	 // Lista de utensilios
    private List<String> listkitchenUtensils;
    
    //Tecnicas usadas en la receta
  //Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador, restaurante o lugar donde se creo y descripcion o instruccion de como es
    @NotNull
    private List<String> listRecipeTechniques;

	// Intrucciones para elaborar la receta
	@NotNull
	private String instructions;

	private String difficulty ;
}
