package com.example.demo.model;

public class ingredientModel {

	private Long id;

	private String nombre;

	private String descripcion; // Información adicional, como historia, origen, etc.
	
	private String categoria;

	private String origen;
	
	public ingredientModel() {
		super();
	}

	public ingredientModel( String nombre, String descripcion, String categoria, String origen) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.categoria = categoria;
		this.origen = origen;
	}

	//Getters And Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	@Override
	public String toString() {
		return "ingredientModel [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", categoria="
				+ categoria + ", origen=" + origen + "]";
	}
}
