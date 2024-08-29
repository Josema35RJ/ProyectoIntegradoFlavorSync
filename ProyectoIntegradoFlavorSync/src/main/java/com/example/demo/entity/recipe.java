package com.example.demo.entity;

import java.util.Arrays;
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
@Table(name = "recipe")
@Data
public class recipe {
	// Identificador único para cada receta.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// Nombre de la receta
	@Column(name = "name", nullable = false)
	@Size(max = 25, message = "The name cannot exceed 25 characters")
	private String name;

	// Cocinero aprendiz, profesional, chef?
	private List<String> level = Arrays.asList("Aprendiz", "Profesional", "Chef");

	// Para cuantas personas es la receta
	@Column(name = "diners", nullable = false)
	@Positive(message = "The diners must be a positive number")
	private List<Integer> diners = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());

	// Cuanto se tarda en elaborar la receta, en minutos
	@Column(name = "preparationTime", nullable = false)
	@Positive(message = "The preparationTime must be a positive number")
	private List<Double> preparationTime = IntStream.rangeClosed(1, 100).asDoubleStream().boxed()
			.collect(Collectors.toList());

	// Si la receta se hace, en horno, microondas
	private List<String> whereItisDone = Arrays.asList("Horno", "Microondas", "Freidora Aceite", "Freidora Aire",
			"Vitroceramica"); // (kitchenappliance)

	// Si la receta es postre, entrante, primer plato...
	private List<String> category = Arrays.asList("Postre", "Entrante", "Primer Plato");

	// En cada comentario se valora, la receta, de esa media, se otendra esta nota
	@Positive(message = "The punctuation must be a positive number")
	private float averageRating = 0;

	// Cada cocinero podra valorar la receta (menos el creador)
	private List<comment> listComments;

	// lista de ingredientes necesarios
	private List<ingredient> listIngredients = Arrays.asList(
	        new ingredient("Harina", "Gramos"),
	        new ingredient("Azúcar", "Gramos"),
	        new ingredient("Huevos", "Unidad"),
	        new ingredient("Leche", "Mililitro"),
	        new ingredient("Mantequilla", "Gramos"),
	        new ingredient("Sal", "Pizca"),
	        new ingredient("Polvo de hornear", "Cucharadas"),
	        new ingredient("Vainilla", "Mililitro"),
	        new ingredient("Aceite", "Mililitro"),
	        new ingredient("Chocolate", "Gramos"),
	        new ingredient("Nueces", "Gramos"),
	        new ingredient("Canela", "Cucharadas"),
	        new ingredient("Miel", "Mililitro"),
	        new ingredient("Yogur", "Gramos"),
	        new ingredient("Queso", "Gramos"),
	        new ingredient("Tomate", "Unidad"),
	        new ingredient("Cebolla", "Unidad"),
	        new ingredient("Ajo", "Dientes"),
	        new ingredient("Pimiento", "Unidad"),
	        new ingredient("Zanahoria", "Unidad")
	    );
	 // Lista de utensilios
    private List<String> listkitchenUtensils = Arrays.asList(
        "Batidora", "Bol", "Cuchara", "Taza medidora", "Horno",
        "Sartén", "Cuchillo", "Tabla de cortar", "Rallador", "Colador",
        "Cazo", "Espátula", "Tenedor", "Cucharón", "Tijeras de cocina",
        "Pelador", "Mortero", "Moldes para hornear", "Rodillo", "Termómetro de cocina"
    );

	// Intrucciones para elaborar la receta
	@NotNull
	private String instructions;

	private List<String> difficulty = Arrays.asList("Facil", "Medio", "Dificil", "Experto");
}
