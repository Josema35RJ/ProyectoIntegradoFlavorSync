package com.example.demo.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class commentModel {
	// Identificador único para cada comentario.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Id del cocinero, que escribe el comentario
	@Column(name = "cookId", nullable = false)
	private int cookId;

	// Id de la receta del comentario
	@Column(name = "recipeId", nullable = false)
	private int recipeId;

	// El comentario que escribe
	@Column(name = "Description", nullable = false)
	@Size(max = 255, message = "The description cannot exceed 255 characters")
	private String Description;

	// Puntuacion que le da, al eborarla el y probarla ( esta puntuacion hara media
	// con las demas, para obtener las valoracion a la receta)
	@Column(name = "punctuaction", nullable = false)
	@Positive(message = "The punctuation must be a positive number")
	private List<Integer> punctuation = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());

	public commentModel(Integer id, int cookId, int recipeId,
			@Size(max = 255, message = "The description cannot exceed 255 characters") String description,
			@Positive(message = "The punctuation must be a positive number") List<Integer> punctuation) {
		super();
		this.id = id;
		this.cookId = cookId;
		this.recipeId = recipeId;
		Description = description;
		this.punctuation = punctuation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCookId() {
		return cookId;
	}

	public void setCookId(int cookId) {
		this.cookId = cookId;
	}

	public int getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public List<Integer> getPunctuation() {
		return punctuation;
	}

	public void setPunctuation(List<Integer> punctuation) {
		this.punctuation = punctuation;
	}

	@Override
	public String toString() {
		return "commentModel [id=" + id + ", cookId=" + cookId + ", recipeId=" + recipeId + ", Description="
				+ Description + ", punctuation=" + punctuation + "]";
	}
	
}
