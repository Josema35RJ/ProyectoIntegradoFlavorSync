package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	private String nickName;

	// correo para que entre en su cuenta
	@Column(name = "email", nullable = false)
	@Email
	@Size(max = 100, message = "The email cannot exceed 100 characters")
	@NotBlank(message = "The email is required")
	private String username;
	
	private boolean confirm_email = false;

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
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<culinaryTechniquesModel> listCulinaryTechniques = new ArrayList<>();

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
	private List<recipeModel> listRecipes;

	// Lista de recetas favoritas de este cocinero (pueden ser recetas suyas o de
	// otros)
	private List<recipeModel> listRecipesFavorites;

	// Imagen del perfil
	private byte[] imagePerfil;

	// Imagen o imagenes del cocinero guardada en base64
	private List<byte[]> imagesCook = new ArrayList<>();
	 
	private LocalDateTime createDate;
	
	private LocalDateTime updateDate;

	public cookModel() {
		super();
	}

//Constructor, con imagen de perfil del cocinero 
	public cookModel(
			@Size(max = 100, message = "The firstName cannot exceed 100 characters") @NotBlank(message = "firstName is required") String firstName,
			@Size(max = 100, message = "The lastName cannot exceed 100 characters") @NotBlank(message = "lastName is required") String lastName,
			@Size(max = 100, message = "The nickName cannot exceed 100 characters") @NotBlank(message = "nickName is required") String nickName,
			@Email @Size(max = 100, message = "The email cannot exceed 100 characters") @NotBlank(message = "The email is required") String username,
			@NotBlank(message = "Age is required") @Positive(message = "The height must be a positive number") Date birthDate,
			@Size(max = 100, message = "The city cannot exceed 100 characters") @NotBlank(message = "city is required") String city,
			@Size(max = 100, message = "The country cannot exceed 100 characters") @NotBlank(message = "country is required") String country,
			@NotBlank(message = "The password is required") String password, List<String> listSpecialty,List<culinaryTechniquesModel> listRecipeTechniques,
			@Positive(message = "The experience must be a positive number") int experience, byte[] imagePerfil) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickName = nickName;
		this.username = username;
		this.confirm_email = false;
		this.birthDate = birthDate;
		this.city = city;
		this.country = country;
		this.password = password;
		this.enabled = false;
		this.listSpecialty = listSpecialty;
		this.listCulinaryTechniques = listRecipeTechniques;
		this.experience = experience;
		this.role = "ROL_COOKAPRENDIZ";
		this.punctuation = 0;
		this.imagePerfil = imagePerfil;
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

	public boolean isConfirm_email() {
		return confirm_email;
	}

	public void setConfirm_email(boolean confirm_email) {
		this.confirm_email = confirm_email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public List<culinaryTechniquesModel> getListCulinaryTechniques() {
		return listCulinaryTechniques;
	}

	public void setListCulinaryTechniques(List<culinaryTechniquesModel> listCulinaryTechniques) {
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

	public byte[] getImagePerfil() {
		return imagePerfil;
	}

	public void setImagePerfil(byte[] imagePerfil) {
		this.imagePerfil = imagePerfil;
	}

	public List<byte[]> getImagesCook() {
		return imagesCook;
	}

	public void setImagesCook(List<byte[]> imagesCook) {
		this.imagesCook = imagesCook;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "cookModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nickName=" + nickName
				+ ", username=" + username + ", birthDate=" + birthDate + ", city=" + city + ", country=" + country
				+ ", password=" + password + ", enabled=" + enabled + ", listSpecialty=" + listSpecialty
				+ ", listCulinaryTechniques=" + listCulinaryTechniques + ", experience=" + experience + ", role=" + role
				+ ", punctuation=" + punctuation + ", listRecipes=" + listRecipes + ", listRecipesFavorites="
				+ listRecipesFavorites + ", imagePerfil=" + Arrays.toString(imagePerfil) + ", imagesCook=" + imagesCook
				+ ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}
}
