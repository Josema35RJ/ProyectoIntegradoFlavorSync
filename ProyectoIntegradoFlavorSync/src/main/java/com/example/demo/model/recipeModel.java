package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class recipeModel {
	// Identificador único para cada receta.
	private Integer id;

	// Nombre de la receta
	private String name;

	// Cocinero aprendiz, profesional, chef?
	private String level;

	// Para cuantas personas es la receta
	private int diners;

	// Cuanto se tarda en elaborar la receta, en minutos
	private float preparationTime;

	// Si la receta se hace, en horno, microondas
	private List<String> whereItisDone = new ArrayList<>(); // (kitchenappliance)

	// Si la receta es postre, entrante, primer plato...
	private List<String> category = new ArrayList<>();

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
	@JsonIgnore
	private List<commentModel> listComments = new ArrayList<>();

	// Lista de utensilios
	private List<String> listkitchenUtensils = new ArrayList<>();

	// Tecnicas usadas en la receta
	// Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador,
	// restaurante o lugar donde se creo y descripcion o instruccion de como es
	@NotNull
	private List<culinaryTechniquesModel> listRecipeTechniques = new ArrayList<>();

	// Intrucciones para elaborar la receta
	@NotNull
	private String instructions;

	private String difficulty;

	// : Información sobre posibles ingredientes alérgenos (como gluten, lactosa,
	// frutos secos) o
	// si la receta es apta para personas con necesidades dietéticas especiales
	// (veganos, vegetarianos,
	// sin gluten, etc.).
	private List<String> AllergensAndDietaryRestrictions = new ArrayList<>();

	// Algunas recetas incluyen una tabla con información sobre las calorías,
	// grasas, carbohidratos,
	private nutritionalInformationModel nutritionalInformation;

	// Consejos útiles, variantes de la receta, sugerencias de presentación, o
	// detalles sobre cómo almacenar el platillo.
	// También pueden incluir recomendaciones para sustituir ingredientes.
	private String grades;

	// Si es relevante, la receta puede incluir detalles sobre su origen cultural,
	// tradicional, o inspiración de algún lugar o persona.
	// Esto le da un toque especial y contexto histórico al platillo.
	private String History;

	// pais de la receta
	private String Country;

	// ciudad de la receta
	private String city;

	// Imagen o imagenes de la receta
	private List<String> imagesRecipe = new ArrayList<>();

	// Imagen para la receta que sale en la pantalla principal, imagen perfil de la
	// receta
	private String imageRecipePerfil;

	// Video de la elaboracion guardado en base 64
	private MovieModel video;

	private List<MovieModel> listVideo;

	// Relación many-to-many usando la clase RecipeIngredient como entidad
	// intermedia
	private List<ingredientRecipeModel> ingredients = new ArrayList<>();

	// Fecha de creacion de la receta
	private LocalDate createDate;

	// Fecha de la actualizacion de esta receta
	private LocalDate updateDate;

	// Contador de likes
	private int likesCount;

	public recipeModel(String name, int diners, float preparationTime, List<String> whereItisDone, String grades,
			List<String> category, List<String> listkitchenUtensils, List<culinaryTechniquesModel> listRecipeTechniques,
			String instructions, String difficulty, List<String> allergensAndDietaryRestrictions, String country,
			String city) {
		super();
		this.name = name;
		this.diners = diners;
		this.preparationTime = preparationTime;
		this.whereItisDone = whereItisDone;
		this.grades = grades;
		this.category = category;
		this.listkitchenUtensils = listkitchenUtensils;
		this.listRecipeTechniques = listRecipeTechniques;
		this.instructions = instructions;
		this.difficulty = difficulty;
		this.AllergensAndDietaryRestrictions = allergensAndDietaryRestrictions;
		this.Country = country;
		this.city = city;
		this.createDate = LocalDate.now();

	}

	public recipeModel() {
		// TODO Auto-generated constructor stub
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

	public float getAverageRating() {
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

	public List<String> getAllergensAndDietaryRestrictions() {
		return AllergensAndDietaryRestrictions;
	}

	public void setAllergensAndDietaryRestrictions(
			@Size(max = 455, message = "The name cannot exceed 455 characters") List<String> allergensAndDietaryRestrictions) {
		AllergensAndDietaryRestrictions = allergensAndDietaryRestrictions;
	}

	public nutritionalInformationModel getNutritionalInformation() {
		return nutritionalInformation;
	}

	public void setNutritionalInformation(nutritionalInformationModel nutritionalInformation) {
		this.nutritionalInformation = nutritionalInformation;
	}

	public String getGrades() {
		return grades;
	}

	public void setGrades(String grades) {
		this.grades = grades;
	}

	public String getHistory() {
		return History;
	}

	public void setOrigin(String history) {
		History = history;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getImagesRecipe() {
		return imagesRecipe;
	}

	public void setImagesRecipe(List<String> imagesRecipe) {
		this.imagesRecipe.addAll(imagesRecipe);
	}

	public List<ingredientRecipeModel> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<ingredientRecipeModel> Ingredients) {
		this.ingredients = Ingredients;
	}

	public void setHistory(String history) {
		History = history;
	}

	public String getImageRecipePerfil() {
		return imageRecipePerfil;
	}

	public void setImageRecipePerfil(String imageRecipePerfil) {
		this.imageRecipePerfil = imageRecipePerfil;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}

	// Getters y Setters
	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public MovieModel getVideo() {
		return video;
	}

	public void setVideo(MovieModel video) {
		this.video = video;
	}

	public List<MovieModel> getListVideo() {
		return listVideo;
	}

	public void setListVideo(List<MovieModel> listVideo) {
		this.listVideo = listVideo;
	}

	@Override
	public String toString() {
		return "recipeModel [id=" + id + ", name=" + name + ", level=" + level + ", diners=" + diners
				+ ", preparationTime=" + preparationTime + ", whereItisDone=" + whereItisDone + ", category=" + category
				+ ", averageRating=" + averageRating + ", listComments=" + listComments + ", listkitchenUtensils="
				+ listkitchenUtensils + ", listRecipeTechniques=" + listRecipeTechniques + ", instructions="
				+ instructions + ", difficulty=" + difficulty + ", AllergensAndDietaryRestrictions="
				+ AllergensAndDietaryRestrictions + ", nutritionalInformation=" + nutritionalInformation + ", grades="
				+ grades + ", History=" + History + ", Country=" + Country + ", city=" + city + ", imagesRecipe="
				+ imagesRecipe + ", Ingredientes=" + ingredients + "]";
	}
}
