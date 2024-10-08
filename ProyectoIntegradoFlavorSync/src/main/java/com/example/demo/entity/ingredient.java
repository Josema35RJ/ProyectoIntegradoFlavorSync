package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "ingredient")
@Data
public class ingredient {

	// Identificador único para cada comentario.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	//Nombre del ingrediente
	@Column(name = "name", nullable = false)
	private String name;  

    
}
