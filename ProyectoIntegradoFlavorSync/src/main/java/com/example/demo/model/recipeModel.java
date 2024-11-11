package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.demo.entity.nutritionalInformation;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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
	private float preparationTime;

	// Si la receta se hace, en horno, microondas
	private List<String> whereItisDone = new ArrayList<>(); // (kitchenappliance)

	// Si la receta es postre, entrante, primer plato...
	private List<String> category = new ArrayList<>();

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
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
	// proteínas y otros nutrientes por porción.
	@Size(max = 455, message = "The name cannot exceed 455 characters")
	private nutritionalInformationModel nutritionalInformation;

	// Consejos útiles, variantes de la receta, sugerencias de presentación, o
	// detalles sobre cómo almacenar el platillo.
	// También pueden incluir recomendaciones para sustituir ingredientes.
	@Column(name = "Grades")
	@Size(max = 50, message = "The name cannot exceed 50 characters")
	private String grades;

	// Si es relevante, la receta puede incluir detalles sobre su origen cultural,
	// tradicional, o inspiración de algún lugar o persona.
	// Esto le da un toque especial y contexto histórico al platillo.
	@Column(name = "History")
	@Size(max = 455, message = "The name cannot exceed 455 characters")
	private String History;

	// pais de la receta
	@Column(name = "country", nullable = false)
	@Size(max = 100, message = "The country cannot exceed 100 characters")
	@NotBlank(message = "country is required")
	private String Country;

	// ciudad de la receta
	@Column(name = "city", nullable = false)
	@Size(max = 100, message = "The city cannot exceed 100 characters")
	@NotBlank(message = "city is required")
	private String city;

	// Imagen o imagenes de la receta
	private List<byte[]> imagesRecipe = new ArrayList<>();
	
	//Imagen para la receta que sale en la pantalla principal, imagen perfil de la receta
	private byte[] imageRecipePerfil;

	// Video de la elaboracion
	// private String video;

	// Relación many-to-many usando la clase RecipeIngredient como entidad
	// intermedia
	private List<String> Ingredients = new ArrayList<>();
	
	//Fecha de creacion de la receta 
	private LocalDate createDate;
	
	//Fecha de la actualizacion de esta receta
	private LocalDate updateDate;
	
	public recipeModel(Integer id, String name, int diners, float preparationTime, List<String> whereItisDone,
			List<String> category, List<String> Ingredients, List<String> listkitchenUtensils,
			String instructions, String difficulty, List<String> allergensAndDietaryRestrictions,
			nutritionalInformationModel nutritionalInformation, String country, String city) {
		super();
		this.id = id;
		this.name = name;
		this.diners = diners;
		this.preparationTime = preparationTime;
		this.whereItisDone = whereItisDone;
		this.category = category;
		this.listkitchenUtensils = listkitchenUtensils;
		this.instructions = instructions;
		this.difficulty = difficulty;
		this.Ingredients = Ingredients;
		this.AllergensAndDietaryRestrictions = allergensAndDietaryRestrictions;
		this.nutritionalInformation = nutritionalInformation;
	    this.Country= country;
		this.city = city;
		this.createDate = LocalDate.now();

	}

	public recipeModel(Integer id, String name, int diners, float preparationTime, List<String> whereItisDone,
			List<String> category, List<String> listkitchenUtensils,
			String instructions, String difficulty, List<String> allergensAndDietaryRestrictions,
			nutritionalInformationModel nutritionalInformation, String grades, String history, String country, String city) {
		super();
		this.id = id;
		this.name = name;
		this.diners = diners;
		this.preparationTime = preparationTime;
		this.whereItisDone = whereItisDone;
		this.category = category;
		this.listkitchenUtensils = listkitchenUtensils;
		this.instructions = instructions;
		this.difficulty = difficulty;
		AllergensAndDietaryRestrictions = allergensAndDietaryRestrictions;
		this.nutritionalInformation = nutritionalInformation;
		this.grades = grades;
		History = history;
		Country = country;
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

	public List<byte[]> getImagesRecipe() {
		return imagesRecipe;
	}

	public void setImagesRecipe(List<byte[]> imagesRecipe) {
		this.imagesRecipe.addAll(imagesRecipe);
	}

	public List<String> getIngredients() {
		return Ingredients;
	}

	public void setIngredients(List<String> Ingredients) {
		this.Ingredients = Ingredients;
	}

	public void setHistory(String history) {
		History = history;
	}

	public byte[] getImageRecipePerfil() {
		return imageRecipePerfil;
	}

	public void setImageRecipePerfil(byte[] imageRecipePerfil) {
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

	@Override
	public String toString() {
		return "recipeModel [id=" + id + ", name=" + name + ", level=" + level + ", diners=" + diners
				+ ", preparationTime=" + preparationTime + ", whereItisDone=" + whereItisDone + ", category=" + category
				+ ", averageRating=" + averageRating + ", listComments=" + listComments + ", listkitchenUtensils="
				+ listkitchenUtensils + ", listRecipeTechniques=" + listRecipeTechniques + ", instructions="
				+ instructions + ", difficulty=" + difficulty + ", AllergensAndDietaryRestrictions="
				+ AllergensAndDietaryRestrictions + ", nutritionalInformation=" + nutritionalInformation + ", grades="
				+ grades + ", History=" + History + ", Country=" + Country + ", city=" + city + ", imagesRecipe="
				+ imagesRecipe + ", Ingredientes=" + Ingredients + "]";
	}
}
