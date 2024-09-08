package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


public class culinaryTechniquesModel {

	// Identificador único para cada tecnica
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Nombre de la tecnica
	@Column(name = "name", nullable = false)
	@Size(max = 100, message = "The name cannot exceed 100 characters")
	@NotBlank(message = "name is required")
	private String name;

	// En que consiste que es
	@Column(name = "description", nullable = false)
	@Size(max = 100, message = "The description cannot exceed 400 characters")
	@NotBlank(message = "description is required")
	private String description;

	// Donde nacio o se creo
	@Column(name = "history", nullable = false)
	@Size(max = 100, message = "The history cannot exceed 100 characters")
	@NotBlank(message = "history is required")
	private String history;

	// Donde nacio o se creo
	@Column(name = "origin", nullable = false)
	@Size(max = 100, message = "The origin cannot exceed 100 characters")
	@NotBlank(message = "origin is required")
	private String Origin;
	
	// Mas adelante se añadira
	// private String img;

	public culinaryTechniquesModel(Integer id,
			@Size(max = 100, message = "The name cannot exceed 100 characters") @NotBlank(message = "name is required") String name,
			@Size(max = 100, message = "The description cannot exceed 400 characters") @NotBlank(message = "description is required") String description,
			@Size(max = 100, message = "The history cannot exceed 100 characters") @NotBlank(message = "history is required") String history,
			@Size(max = 100, message = "The origin cannot exceed 100 characters") @NotBlank(message = "origin is required") String origin) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.history = history;
		Origin = origin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getOrigin() {
		return Origin;
	}

	public void setOrigin(String origin) {
		Origin = origin;
	}

	@Override
	public String toString() {
		return "culinaryTechniques [id=" + id + ", name=" + name + ", description=" + description + ", history="
				+ history + ", Origin=" + Origin + "]";
	}	

}
