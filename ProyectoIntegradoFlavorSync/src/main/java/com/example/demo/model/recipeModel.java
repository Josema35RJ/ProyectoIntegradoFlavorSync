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
import jakarta.validation.constraints.Size;
import lombok.Data;

public class recipeModel {
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
	private float preparationTime ;

	// Si la receta se hace, en horno, microondas
	private List<String> whereItisDone; // (kitchenappliance)

	// Si la receta es postre, entrante, primer plato...
	private List<String> category ;

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	@Positive(message = "The averageRating must be a positive number")
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
	private List<commentModel> listComments;

	// lista de ingredientes necesarios
	private List<ingredientModel> listIngredients;

	// Lista de utensilios
	private List<String> listkitchenUtensils;
	
	 //Tecnicas usadas en la receta
	//Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador, restaurante o lugar donde se creo y descripcion o instruccion de como es
    @NotNull
    private List<culinaryTechniquesModel> listRecipeTechniques;

	// Intrucciones para elaborar la receta
	@NotNull
	private String instructions;

	private String difficulty;

	public recipeModel(Integer id, @Size(max = 25, message = "The name cannot exceed 25 characters") String name,
			String level, @Positive(message = "The diners must be a positive number") int diners,
			@Positive(message = "The preparationTime must be a positive number") float preparationTime,
			List<String> whereItisDone, List<String> category,
			@Positive(message = "The punctuation must be a positive number") float averageRating,
			List<commentModel> listComments, List<ingredientModel> listIngredients, List<String> listkitchenUtensils, List<culinaryTechniquesModel>listRecipeTechniques, 
			@NotNull String instructions, String difficulty) {
		super();
		this.id = id;
		this.name = name;
		this.level = level;
		this.diners = diners;
		this.preparationTime = preparationTime;
		this.whereItisDone = whereItisDone;
		this.category = category;
		this.averageRating = averageRating;
		this.listComments = listComments;
		this.listIngredients = listIngredients;
		this.listkitchenUtensils = listkitchenUtensils;
		this.listRecipeTechniques = listRecipeTechniques;
		this.instructions = instructions;
		this.difficulty = difficulty;
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

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getDiners() {
		return diners;
	}

	public void setDiners(int diners) {
		this.diners = diners;
	}

	public float getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(float preparationTime) {
		this.preparationTime = preparationTime;
	}

	public List<String> getWhereItisDone() {
		return whereItisDone;
	}

	public void setWhereItisDone(List<String> whereItisDone) {
		this.whereItisDone = whereItisDone;
	}

	public List<String> getCategory() {
		return category;
	}

	public void setCategory(List<String> category) {
		this.category = category;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	public List<commentModel> getListComments() {
		return listComments;
	}

	public void setListComments(List<commentModel> listComments) {
		this.listComments = listComments;
	}

	public List<ingredientModel> getListIngredients() {
		return listIngredients;
	}

	public void setListIngredients(List<ingredientModel> listIngredients) {
		this.listIngredients = listIngredients;
	}

	public List<String> getListkitchenUtensils() {
		return listkitchenUtensils;
	}

	public void setListkitchenUtensils(List<String> listkitchenUtensils) {
		this.listkitchenUtensils = listkitchenUtensils;
	}

	public List<culinaryTechniquesModel> getListRecipeTechniques() {
		return listRecipeTechniques;
	}

	public void setListRecipeTechniques(List<culinaryTechniquesModel> listRecipeTechniques) {
		this.listRecipeTechniques = listRecipeTechniques;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "recipeModel [id=" + id + ", name=" + name + ", level=" + level + ", diners=" + diners
				+ ", preparationTime=" + preparationTime + ", whereItisDone=" + whereItisDone + ", category=" + category
				+ ", averageRating=" + averageRating + ", listComments=" + listComments + ", listIngredients="
				+ listIngredients + ", listkitchenUtensils=" + listkitchenUtensils + ", listRecipeTechniques="
				+ listRecipeTechniques + ", instructions=" + instructions + ", difficulty=" + difficulty + "]";
	}
}
