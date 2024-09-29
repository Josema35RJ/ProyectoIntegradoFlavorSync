package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
	@Size(max = 100, message = "The name cannot exceed 100 characters")
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
	private List<String> whereItisDone = new ArrayList<>();

	// Si la receta es postre, entrante, primer plato...
	private String category;

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	@Min(value = 0, message = "The averageRating must be a non-negative number")
	@Max(value = 10, message = "The averageRating must not exceed 10")
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
	@OneToMany(cascade = CascadeType.ALL)
	private List<comment> listComments = new ArrayList<>();

	// lista de ingredientes necesarios
	@OneToMany(cascade = CascadeType.ALL)
	@NotNull
	private List<ingredient> listIngredients = new ArrayList<>();

	// Lista de utensilios
	private List<String> listkitchenUtensils = new ArrayList<>();

	// Tecnicas usadas en la receta
	// Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador,
	// restaurante o lugar donde se creo y descripcion o instruccion de como es
	@NotNull
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<culinaryTechniques> listRecipeTechniques = new ArrayList<>();

	// Intrucciones para elaborar la receta
	@NotNull
	@Column(name = "instructions", nullable = false)
	private String instructions;

	// Es complicado hacer el plato
	@Column(name = "difficulty", nullable = false)
	@Size(max = 25, message = "The name cannot exceed 25 characters")
	private String difficulty;

	// : Información sobre posibles ingredientes alérgenos (como gluten, lactosa,
	// frutos secos) o
	// si la receta es apta para personas con necesidades dietéticas especiales
	// (veganos, vegetarianos,
	// sin gluten, etc.).
	@Column(name = "AllergensAndDietaryRestrictions", nullable = false)
	@Size(max = 455, message = "The name cannot exceed 455 characters")
	private List<String> AllergensAndDietaryRestrictions = new ArrayList<>();

	// Algunas recetas incluyen una tabla con información sobre las calorías,
	// grasas, carbohidratos,
	// proteínas y otros nutrientes por porción.
	@Column(name = "nutritionalInformation")
	@Size(max = 455, message = "The name cannot exceed 455 characters")
	private String nutritionalInformation;

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

	// Imagen o imagenes de la receta guardada en base 64
	@ElementCollection
	private List<byte[]> imagesRecipe = new ArrayList<>();

	// Video de la elaboracion guardado en base 64
	// private byte[] video;

	// Relación many-to-many usando la clase RecipeIngredient como entidad
	// intermedia
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<recipeIngredient> ingredients  = new ArrayList<>();
	
	//Imagen de perfil de la receta
	@NotNull
	@Column(name = "imagenRecipePerfil", nullable = false)
	private byte[] imageRecipePerfil;
}
