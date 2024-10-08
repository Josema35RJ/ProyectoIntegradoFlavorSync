package com.example.demo.model;

public class nutritionalInformationModel {

    private Integer id;

    // Calorías por porción
    private float calories;

    // Grasas en gramos
    private float fat;

    // Carbohidratos en gramos
    private float carbohydrates;

    // Proteínas en gramos
    private float proteins;

    // Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
    private String otherNutrients;
    
	public nutritionalInformationModel() {
		super();
	}

	public nutritionalInformationModel(Integer id, float calories, float fat, float carbohydrates, float proteins,
			String otherNutrients) {
		super();
		this.id = id;
		this.calories = calories;
		this.fat = fat;
		this.carbohydrates = carbohydrates;
		this.proteins = proteins;
		this.otherNutrients = otherNutrients;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getCalories() {
		return calories;
	}

	public void setCalories(float calories) {
		this.calories = calories;
	}

	public float getFat() {
		return fat;
	}

	public void setFat(float fat) {
		this.fat = fat;
	}

	public float getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(float carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public float getProteins() {
		return proteins;
	}

	public void setProteins(float proteins) {
		this.proteins = proteins;
	}

	public String getOtherNutrients() {
		return otherNutrients;
	}

	public void setOtherNutrients(String otherNutrients) {
		this.otherNutrients = otherNutrients;
	}

	@Override
	public String toString() {
		return "nutritionalInformationModel [id=" + id + ", calories=" + calories + ", fat=" + fat + ", carbohydrates="
				+ carbohydrates + ", proteins=" + proteins + ", otherNutrients=" + otherNutrients + "]";
	}
}

