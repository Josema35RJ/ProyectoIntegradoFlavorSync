package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class cookModel {

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
	@NotBlank(message = "Age is required")
	@Positive(message = "The height must be a positive number")
	private int age;

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

	@NotBlank(message = "The enabled is required")
	private boolean enabled;

	// Que cocinas, postres, dulces...
	private List<String> listSpecialty ;

	// Tecnicas usadas en la receta
	// Mas adelante Tecnicas sera otra entidad, con nombre de la tecnica, creador,
	// restaurante o lugar donde se creo y descripcion o instruccion de como es
	@NotNull
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<culinaryTechniquesModel> listCulinaryTechniques = new ArrayList<>();

	// Cuantos años tienes cocinando
	@Positive(message = "The experience must be a positive number")
	private int experience;

	// Segun la puntuacion tendra un rol u otro (empezando por aprendiz)
	private String role;

	// Esta puntuacion se obtiene, en base a la media de notas de las recetas
	@Positive(message = "The punctuaction must be a positive number")
	private int punctuation;

	// Lista de recetas elaboradas o creadas por este cocinero
	private List<recipeModel> listRecipes;

	// Lista de recetas favoritas de este cocinero (pueden ser recetas suyas o de
	// otros)
	private List<recipeModel> listRecipesFavorites;

	public cookModel() {
		super();
	}

	

	public cookModel(Integer id,
			@Size(max = 100, message = "The firstName cannot exceed 100 characters") @NotBlank(message = "firstName is required") String firstName,
			@Size(max = 100, message = "The lastName cannot exceed 100 characters") @NotBlank(message = "lastName is required") String lastName,
			@Size(max = 100, message = "The nickName cannot exceed 100 characters") @NotBlank(message = "nickName is required") String nickName,
			@Email @Size(max = 100, message = "The email cannot exceed 100 characters") @NotBlank(message = "The email is required") String username,
			@NotBlank(message = "Age is required") @Positive(message = "The height must be a positive number") int age,
			@Size(max = 100, message = "The city cannot exceed 100 characters") @NotBlank(message = "city is required") String city,
			@Size(max = 100, message = "The country cannot exceed 100 characters") @NotBlank(message = "country is required") String country,
			@NotBlank(message = "The password is required") String password,
			@NotBlank(message = "The enabled is required") boolean enabled, List<String> listSpecialty,
			@NotNull List<culinaryTechniquesModel> listCulinaryTechniques,
			@Positive(message = "The experience must be a positive number") int experience, String role,
			@Positive(message = "The punctuaction must be a positive number") int punctuation,
			List<recipeModel> listRecipes, List<recipeModel> listRecipesFavorites) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.username = username;
		this.age = age;
		this.city = city;
		this.country = country;
		this.password = password;
		this.enabled = enabled;
		this.listSpecialty = listSpecialty;
		this.listCulinaryTechniques = listCulinaryTechniques;
		this.experience = experience;
		this.role = "Aprendiz";
		this.punctuation = 0;
		this.listRecipes = listRecipes;
		this.listRecipesFavorites = listRecipesFavorites;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getListSpecialty() {
		return listSpecialty;
	}

	public void setListSpecialty(List<String> listSpecialty) {
		this.listSpecialty = listSpecialty;
	}

	public List<culinaryTechniquesModel> getListRecipeTechniques() {
		return listCulinaryTechniques;
	}

	public void setListRecipeTechniques(List<culinaryTechniquesModel> listCulinaryTechniques) {
		this.listCulinaryTechniques = listCulinaryTechniques;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getPunctuation() {
		return punctuation;
	}

	public void setPunctuation(int punctuation) {
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
		return "cookModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nickName=" + nickName
				+ ", username=" + username + ", age=" + age + ", city=" + city + ", country=" + country + ", password="
				+ password + ", enabled=" + enabled + ", listSpecialty=" + listSpecialty + ", listCulinaryTechniques="
				+ listCulinaryTechniques + ", experience=" + experience + ", role=" + role + ", punctuation="
				+ punctuation + ", listRecipes=" + listRecipes + ", listRecipesFavorites=" + listRecipesFavorites + "]";
	}
}
