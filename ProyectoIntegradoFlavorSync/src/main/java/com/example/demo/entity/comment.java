package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
	@Column(name = "description", nullable = false)
	@Size(max = 255, message = "The description cannot exceed 255 characters")
	private String description;

	// Puntuacion que le da, al eborarla el y probarla ( esta puntuacion hara media
	// con las demas, para obtener las valoracion a la receta)
	@Column(name = "punctuation")
	@Min(value = 0, message = "The punctuation must be a non-negative number")
	private int punctuation;

	@ManyToOne
	@JoinColumn(name = "cook_id", referencedColumnName = "id")
	private cook cookId;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

	// Relación recursiva: un comentario puede tener respuestas.
	@ManyToOne
	private comment parentComment; // Comentario al que responde, si lo tiene.

	@OneToMany(mappedBy = "parentComment")
	private List<comment> replies = new ArrayList<>(); // Respuestas a este comentario.
}
