package com.example.demo.model;

public class nutritionalInformationModel {

    private Integer id;

    // Calorías por porción
    private Float calories;

    // Grasas en gramos
    private Float fat;

    // Carbohidratos en gramos
    private Float carbohydrates;

    // Proteínas en gramos
    private Float proteins;

    // Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
    private String otherNutrients;
    
	public nutritionalInformationModel() {
		super();
	}

	public nutritionalInformationModel(Integer id, Float calories, Float fat, Float carbohydrates, Float proteins,
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

	public Float getCalories() {
		return calories;
	}

	public void setCalories(Float calories) {
		this.calories = calories;
	}

	public Float getFat() {
		return fat;
	}

	public void setFat(Float fat) {
		this.fat = fat;
	}

	public Float getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(Float carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public Float getProteins() {
		return proteins;
	}

	public void setProteins(Float proteins) {
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

