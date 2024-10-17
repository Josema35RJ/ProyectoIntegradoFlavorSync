package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class commentModel {
	// Identificador único para cada comentario.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// El comentario que escribe
	@Column(name = "Description", nullable = false)
	@Size(max = 255, message = "The description cannot exceed 255 characters")
	private String Description;

	// Puntuacion que le da, al eborarla el y probarla ( esta puntuacion hara media
	// con las demas, para obtener las valoracion a la receta)
	@Column(name = "punctuaction", nullable = false)
	@Positive(message = "The punctuation must be a positive number")
	private int punctuation;

	public commentModel() {
		super();
	}

	public commentModel( String description, int punctuation) {
		super();
		Description = description;
		this.punctuation = punctuation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getPunctuation() {
		return punctuation;
	}

	public void setPunctuation(int punctuation) {
		this.punctuation = punctuation;
	}

	@Override
	public String toString() {
		return "commentModel [id=" + id + ", Description="
				+ Description + ", punctuation=" + punctuation + "]";
	}
	
}
