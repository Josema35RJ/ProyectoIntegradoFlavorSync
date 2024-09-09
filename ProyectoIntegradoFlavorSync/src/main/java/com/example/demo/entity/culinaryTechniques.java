package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "culinaryTechniques")
@Data
public class culinaryTechniques {

	// Identificador único para cada tecnica
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	//Nombre de la tecnica
	@Column(name = "name", nullable = false)
	@Size(max = 100, message = "The name cannot exceed 100 characters")
	@NotBlank(message = "name is required")
	private String name;
	
	//En que consiste que es
	@Column(name = "description", nullable = false)
	@Size(max = 100, message = "The description cannot exceed 400 characters")
	@NotBlank(message = "description is required")
	private String description;

	//Donde nacio o se creo
	@Column(name = "history", nullable = false)
	@Size(max = 100, message = "The history cannot exceed 100 characters")
	@NotBlank(message = "history is required")
		private String history;
		
	//Donde nacio o se creo
	@Column(name = "country", nullable = false)
	@Size(max = 100, message = "The country cannot exceed 100 characters")
	@NotBlank(message = "country is required")
	private String Country;
	
	//Mas adelante se añadira
	//private String img;
}
