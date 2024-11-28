import 'dart:convert';

class NutritionalInformation {
  // Identificador único para la información nutricional.
  int? id;

  // Calorías por porción
  double calories;

  // Grasas en gramos
  double fat;

  // Carbohidratos en gramos
  double carbohydrates;

  // Proteínas en gramos
  double proteins;

  // Otros nutrientes (opcional, por ejemplo, fibra, sodio, etc.)
  String? otherNutrients;

  // Constructor
  NutritionalInformation({
    this.id,
    required this.calories,
    required this.fat,
    required this.carbohydrates,
    required this.proteins,
    this.otherNutrients,
  });

  // Factory constructor to create an instance from a map (for JSON parsing)
  factory NutritionalInformation.fromMap(Map<String, dynamic> map) {
    return NutritionalInformation(
      id: map['id'],
      calories: map['calories']?.toDouble() ?? 0.0,
      fat: map['fat']?.toDouble() ?? 0.0,
      carbohydrates: map['carbohydrates']?.toDouble() ?? 0.0,
      proteins: map['proteins']?.toDouble() ?? 0.0,
      otherNutrients: map['otherNutrients'],
    );
  }

  // Method to convert the instance to a map (for JSON serialization)
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'calories': calories,
      'fat': fat,
      'carbohydrates': carbohydrates,
      'proteins': proteins,
      'otherNutrients': otherNutrients,
    };
  }

  // Converts the object to a JSON string
  String toJson() => jsonEncode(toMap());

  // Creates an instance from a JSON string
  factory NutritionalInformation.fromJson(String source) => NutritionalInformation.fromMap(jsonDecode(source));

  @override
  String toString() {
    return 'NutritionalInformation(id: $id, calories: $calories, fat: $fat, carbohydrates: $carbohydrates, proteins: $proteins)';
  }
}
