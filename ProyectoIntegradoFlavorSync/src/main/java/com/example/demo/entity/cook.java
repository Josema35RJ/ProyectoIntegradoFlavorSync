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
	@Column(name = "firstname", nullable = false)
	@Size(max = 100, message = "The firstname cannot exceed 100 characters")
	@NotBlank(message = "firstname is required")
	private String firstname;

	// apellidos del cocinero
	@Column(name = "surname", nullable = false)
	@Size(max = 100, message = "The surname cannot exceed 100 characters")
	@NotBlank(message = "surname is required")
	private String surname;

	// correo para que entre en su cuenta
	@Column(name = "email", nullable = false)
	@Email
	@Size(max = 100, message = "The email cannot exceed 100 characters")
	@NotBlank(message = "The email is required")
	private String email;

	// edad del cocinero
	@NotBlank(message = "Age is required")
	@Positive(message = "The height must be a positive number")
	private List<Integer> age = IntStream.rangeClosed(18, 100).boxed().collect(Collectors.toList());;

	// contraseña para que entre en su cuenta
	@Column(name = "password", nullable = false)
	@NotBlank(message = "The password is required")
	private String password;

	// Que cocinas, postres, dulces...
	private List<String> listSpecialty = Arrays.asList("Todas", "Pastelero", "Carnes", "Pescado y Mariscos", "Verduras",
			"Salsas", "Parrilla");

	// Cuantos años tienes cocinando
	@Positive(message = "The experience must be a positive number")
	private List<Integer> experience = IntStream.rangeClosed(0,60).boxed().collect(Collectors.toList());;

	// Segun la puntuacion tendra un rol u otro (empezando por aprendiz)
	private List<String> rol = Arrays.asList("Amateur", "Aprendiz", "Profesional", "Chef");

	// Esta puntuacion se obtiene, en base a la media de notas de las recetas
	@Positive(message = "The punctuaction must be a positive number")
	private List<Integer> punctuation = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

	// Lista de recetas elaboradas o creadas por este cocinero
	private List<recipe> listRecipes;

	// Lista de recetas favoritas de este cocinero (pueden ser recetas suyas o de
	// otros)
	private List<recipe> listRecipesFavorites;
}
