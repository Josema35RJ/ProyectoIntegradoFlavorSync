

// Definimos la clase del modelo
class CulinaryTechniques {
  // Identificador único para cada técnica
  final int? id;

  // Nombre de la técnica
  final String name;

  // Descripción de la técnica
  final String description;

  // Historia de la técnica
  final String history;

  // País de origen de la técnica
  final String origin;

  // Constructor
  CulinaryTechniques({
    this.id,
    required this.name,
    required this.description,
    required this.history,
    required this.origin,
  });

  // Método de fábrica para crear una instancia desde JSON
  factory CulinaryTechniques.fromJson(Map<String, dynamic> json) {
    return CulinaryTechniques(
      id: json['id'] as int?,
      name: json['name'] as String,
      description: json['description'] as String,
      history: json['history'] as String,
      origin: json['origin'] as String,
    );
  }

  // Método para convertir el modelo en un mapa de JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'description': description,
      'history': history,
      'origin': origin,
    };
  }

  // Método para mostrar la representación del objeto como String (opcional)
  @override
  String toString() {
    return 'CulinaryTechniques{id: $id, name: $name, description: $description, history: $history, origin: $origin}';
  }
}
