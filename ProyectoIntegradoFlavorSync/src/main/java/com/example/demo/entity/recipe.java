package com.example.demo.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@Column(name = "whereItisDone", nullable = false)
	private List<String> whereItisDone = new ArrayList<>();

	// Si la receta es postre, entrante, primer plato...
	private List<String> category = new ArrayList<>();

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	@Min(value = 0, message = "The averageRating must be a non-negative number")
	@Max(value = 10, message = "The averageRating must not exceed 10")
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
	@OneToMany(cascade = CascadeType.ALL)
	private List<comment> listComments = new ArrayList<>();

	// Lista de utensilios
	@Column(name = "listkitchenUtensils", nullable = false)
	private List<String> listkitchenUtensils = new ArrayList<>();

	// Tecnicas usadas en la receta
	// Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador,
	// restaurante o lugar donde se creo y descripcion o instruccion de como es
	@NotNull
	@ManyToMany(cascade = CascadeType.PERSIST)
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
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nutritional_information_id")
	private nutritionalInformation nutritionalInformation;

	// Consejos útiles, variantes de la receta, sugerencias de presentación, o
	// detalles sobre cómo almacenar el platillo.
	// También pueden incluir recomendaciones para sustituir ingredientes.
	@Column(name = "Grades")
	@Size(max = 255, message = "The name cannot exceed 50 characters")
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
	@Lob // Indica que el campo debe ser tratado como un tipo grande
	@Column(name = "imagesRecipe", columnDefinition = "LONGBLOB") // Define el tipo específico de la columna
	@ElementCollection
	private List<byte[]> imagesRecipe = new ArrayList<>();

	// Video de la elaboracion guardado en base 64
	// private byte[] video;
	@ElementCollection
	private List<String> Ingredients = new ArrayList<>();

	// Imagen de perfil de la receta
	@Lob // Indica que el campo debe ser tratado como un tipo grande
	@Column(name = "imagenRecipePerfil", columnDefinition = "LONGBLOB") // Define el tipo específico de la columna
	private byte[] imageRecipePerfil;

	@Column(name = "createDate")
	private LocalDate createDate;

	@Column(name = "updateDate")
	private LocalDate updateDate;

	// Contador de likes
	private int likesCount;
}
