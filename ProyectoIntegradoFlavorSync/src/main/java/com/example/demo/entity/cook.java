package com.example.demo.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	private String firstName;

	// apellidos del cocinero
	@Column(name = "lastName", nullable = false)
	@Size(max = 100, message = "The lastName cannot exceed 100 characters")
	@NotBlank(message = "lastName is required")
	private String lastName;

	// apodo del cocinero
	@Column(name = "nickName", nullable = false)
	@Size(max = 100, message = "The nickName cannot exceed 100 characters")
	@NotBlank(message = "nickName is required")
	private String nickName;

	// correo para que entre en su cuenta
	@Column(name = "email", nullable = false)
	@Email
	@Size(max = 100, message = "The email cannot exceed 100 characters")
	@NotBlank(message = "The email is required")
	private String username;

	// edad del cocinero
	@NotNull(message = "birthDate is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthDate; 

	// ciudad del cocinero
	@Column(name = "city", nullable = false)
	@Size(max = 100, message = "The city cannot exceed 100 characters")
	@NotBlank(message = "city is required")
	private String city;

	// pais del cocinero
	@Column(name = "country", nullable = false)
	@Size(max = 100, message = "The country cannot exceed 100 characters")
	@NotBlank(message = "country is required")
	private String country;

	// contraseña para que entre en su cuenta
	@Column(name = "password", nullable = false)
	@NotBlank(message = "The password is required")
	private String password;

	private boolean enabled;

	// Que cocinas, postres, dulces...
	private List<String> listSpecialty;

	// Tecnicas usadas en la receta
	// Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador,
	// restaurante o lugar donde se creo y descripcion o instruccion de como es
	@NotNull
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<culinaryTechniques> listCulinaryTechniques = new ArrayList<>();

	// Cuantos años tienes cocinando
	@Positive(message = "The experience must be a positive number")
	private int experience;

	// Segun la puntuacion tendra un rol u otro (empezando por aprendiz)
	private String role;

	// Esta puntuacion se obtiene, en base a la media de notas de las recetas
	@Min(value = 0, message = "The punctuation must be a non-negative number")
	@Max(value = 10, message = "The punctuation must not exceed 10")
	private int punctuation;

	// Lista de recetas elaboradas o creadas por este cocinero
	@OneToMany()
	private List<recipe> listRecipes;

	// Lista de recetas favoritas de este cocinero (pueden ser recetas suyas o de
	// otros)
	@OneToMany()
	private List<recipe> listRecipesFavorites;

	// Imagen del perfil
	@Lob // Indica que el campo debe ser tratado como un tipo grande
	@Column(name = "imagePerfil", columnDefinition = "LONGBLOB") // Define el tipo específico de la columna
	private byte[] imagePerfil;

	// Imagen o imagenes del cocinero guardada en base64
	@Lob // Indica que el campo debe ser tratado como un tipo grande
	@Column(name = "imagesCook", columnDefinition = "LONGBLOB") // Define el tipo específico de la columna
	@ElementCollection
	private List<byte[]> imagesCook = new ArrayList<>();

}
