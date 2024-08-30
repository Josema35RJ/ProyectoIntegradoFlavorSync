package com.example.demo.entity;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "cook")
@Data
public class cook {

	// Identificador único para cada cocinero
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Nombre del cocinero
	@Column(name = "firstName", nullable = false)
	@Size(max = 100, message = "The firstName cannot exceed 100 characters")
	@NotBlank(message = "firstName is required")
	private String firstname;

	// apellidos del cocinero
	@Column(name = "lastName", nullable = false)
	@Size(max = 100, message = "The lastName cannot exceed 100 characters")
	@NotBlank(message = "lastName is required")
	private String lastName;

	// correo para que entre en su cuenta
	@Column(name = "email", nullable = false)
	@Email
	@Size(max = 100, message = "The email cannot exceed 100 characters")
	@NotBlank(message = "The email is required")
	private String email;

	// edad del cocinero
	@NotBlank(message = "Age is required")
	@Positive(message = "The height must be a positive number")
	private int age;

	// contraseña para que entre en su cuenta
	@Column(name = "password", nullable = false)
	@NotBlank(message = "The password is required")
	private String password;
	
	@NotBlank(message = "The enabled is required")
	private boolean enabled;

	// Que cocinas, postres, dulces...
	private List<String> listSpecialty;
	
	 //Tecnicas usadas en la receta
	  //Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador, restaurante o lugar donde se creo y descripcion o instruccion de como es
	    @NotNull
	    private List<String> listRecipeTechniques;


	// Cuantos años tienes cocinando
	@Positive(message = "The experience must be a positive number")
	private int experience;

	// Segun la puntuacion tendra un rol u otro (empezando por aprendiz)
	private String rol ;

	// Esta puntuacion se obtiene, en base a la media de notas de las recetas
	@Positive(message = "The punctuaction must be a positive number")
	private int punctuation;

	// Lista de recetas elaboradas o creadas por este cocinero
	private List<recipe> listRecipes;

	// Lista de recetas favoritas de este cocinero (pueden ser recetas suyas o de
	// otros)
	private List<recipe> listRecipesFavorites;
}
