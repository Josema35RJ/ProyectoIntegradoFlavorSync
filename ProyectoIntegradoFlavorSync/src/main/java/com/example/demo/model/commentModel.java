package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class commentModel {

	// Identificador comentarios
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Size(max = 255, message = "The description cannot exceed 255 characters")
	private String description;

	@Positive(message = "The punctuation must be a positive number")
	private int punctuation;

	private cookModel cookId;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

	// Relación recursiva: un comentario puede tener respuestas.
	private commentModel parentComment; // Comentario al que responde, si lo tiene.

	private List<commentModel> replies = new ArrayList<>(); // Respuestas a este comentario.

	public commentModel() {
		super();
	}
	
	public commentModel(
			@Size(max = 255, message = "The description cannot exceed 255 characters") String description) {
		super();
		this.description = description;
		this.createDate = LocalDateTime.now();
	}

	public commentModel(
			@Size(max = 255, message = "The description cannot exceed 255 characters") String description,
			@Positive(message = "The punctuation must be a positive number") int punctuation) {
		super();
		this.description = description;
		this.punctuation = punctuation;
		this.createDate = LocalDateTime.now();
	}

	// Getters y setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPunctuation() {
		return punctuation;
	}

	public void setPunctuation(int punctuation) {
		this.punctuation = punctuation;
	}

	public cookModel getCookId() {
		return cookId;
	}

	public void setCookId(cookModel cookId) {
		this.cookId = cookId;
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

	public commentModel getParentComment() {
		return parentComment;
	}

	public void setParentComment(commentModel parentComment) {
		this.parentComment = parentComment;
	}

	public List<commentModel> getReplies() {
		return replies;
	}

	public void setReplies(List<commentModel> replies) {
		this.replies = replies;
	}

	@Override
	public String toString() {
		return "commentModel [id=" + id + ", description=" + description + ", punctuation=" + punctuation + ", createDate=" + createDate + ", updateDate=" + updateDate + "]";
	}
}
