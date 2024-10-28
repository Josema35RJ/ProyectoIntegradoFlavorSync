package com.example.demo.model;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class commentModel {
	// Identificador único para cada comentario.

	private Integer id;

	// El comentario que escribe

	private String Description;

	// Puntuacion que le da, al eborarla el y probarla ( esta puntuacion hara media
	// con las demas, para obtener las valoracion a la receta)

	private int punctuation;

	private cookModel cookId;

	public commentModel() {
		super();
	}

	public commentModel(@Size(max = 255, message = "The description cannot exceed 255 characters") String description,
			@Positive(message = "The punctuation must be a positive number") int punctuation, cookModel cookId) {
		super();
		Description = description;
		this.punctuation = punctuation;
		this.cookId = cookId;
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

	public cookModel getUserId() {
		return cookId;
	}

	public void setUserId(cookModel userId) {
		this.cookId = userId;
	}

	@Override
	public String toString() {
		return "commentModel [id=" + id + ", Description=" + Description + ", punctuation=" + punctuation + ", userId="
				+ cookId + "]";
	}

}
