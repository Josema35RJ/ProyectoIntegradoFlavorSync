package com.example.demo.entity;

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

@Entity
@Table(name = "comment")
@Data
public class comment {
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
	private List<Integer> punctuation = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());;
}
