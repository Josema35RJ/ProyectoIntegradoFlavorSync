package com.example.demo.entity;

import com.example.demo.model.cookModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class comment {
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
	private int punctuation ;
	
	@OneToOne(cascade = CascadeType.ALL)
	private cook userId;
}
