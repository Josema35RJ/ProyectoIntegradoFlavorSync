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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class cookModel {

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
	private String username;

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
	private List<recipeModel> listRecipes;

	// Lista de recetas favoritas de este cocinero (pueden ser recetas suyas o de
	// otros)
	private List<recipeModel> listRecipesFavorites;

	public cookModel() {
		super();
	}

	public cookModel(Integer id,
			@Size(max = 100, message = "The firstname cannot exceed 100 characters") @NotBlank(message = "firstname is required") String firstname,
			@Size(max = 100, message = "The surname cannot exceed 100 characters") @NotBlank(message = "surname is required") String surname,
			@Email @Size(max = 100, message = "The email cannot exceed 100 characters") @NotBlank(message = "The email is required") String username,
			@NotBlank(message = "Age is required") @Positive(message = "The height must be a positive number") List<Integer> age,
			@NotBlank(message = "The password is required") String password, List<String> listSpecialty,
			@Positive(message = "The experience must be a positive number") List<Integer> experience, List<String> rol,
			@Positive(message = "The punctuaction must be a positive number") List<Integer> punctuation,
			List<recipeModel> listRecipes, List<recipeModel> listRecipesFavorites) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.surname = surname;
		this.username = username;
		this.age = age;
		this.password = password;
		this.listSpecialty = listSpecialty;
		this.experience = experience;
		this.rol = rol;
		this.punctuation = punctuation;
		this.listRecipes = listRecipes;
		this.listRecipesFavorites = listRecipesFavorites;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Integer> getAge() {
		return age;
	}

	public void setAge(List<Integer> age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getListSpecialty() {
		return listSpecialty;
	}

	public void setListSpecialty(List<String> listSpecialty) {
		this.listSpecialty = listSpecialty;
	}

	public List<Integer> getExperience() {
		return experience;
	}

	public void setExperience(List<Integer> experience) {
		this.experience = experience;
	}

	public List<String> getRol() {
		return rol;
	}

	public void setRol(List<String> rol) {
		this.rol = rol;
	}

	public List<Integer> getPunctuation() {
		return punctuation;
	}

	public void setPunctuation(List<Integer> punctuation) {
		this.punctuation = punctuation;
	}

	public List<recipeModel> getListRecipes() {
		return listRecipes;
	}

	public void setListRecipes(List<recipeModel> listRecipes) {
		this.listRecipes = listRecipes;
	}

	public List<recipeModel> getListRecipesFavorites() {
		return listRecipesFavorites;
	}

	public void setListRecipesFavorites(List<recipeModel> listRecipesFavorites) {
		this.listRecipesFavorites = listRecipesFavorites;
	}

	@Override
	public String toString() {
		return "cookModel [id=" + id + ", firstname=" + firstname + ", surname=" + surname + ", email=" + username
				+ ", age=" + age + ", password=" + password + ", listSpecialty=" + listSpecialty + ", experience="
				+ experience + ", rol=" + rol + ", punctuation=" + punctuation + ", listRecipes=" + listRecipes
				+ ", listRecipesFavorites=" + listRecipesFavorites + "]";
	}
	
}
