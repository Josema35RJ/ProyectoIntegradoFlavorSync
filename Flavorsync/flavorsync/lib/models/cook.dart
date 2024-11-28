import 'package:flavorsync/models/culinaryTechniques.dart';
import 'package:flavorsync/models/recipe.dart';

class Cook {
  // Identificador único para cada cocinero
  int? id;

  // Nombre del cocinero
  String firstName;

  // Apellidos del cocinero
  String lastName;

  // Apodo del cocinero
  String nickName;

  // Correo para que entre en su cuenta
  String username;

  // Confirmación del correo
  bool? confirmEmail;

  // Edad del cocinero (fecha de nacimiento)
  DateTime birthDate;

  // Ciudad del cocinero
  String city;

  // País del cocinero
  String country;

  // Contraseña para que entre en su cuenta
  String password;

  // Estado de la cuenta (habilitado o no)
  bool? enabled;

  // Que cocina (listado de especialidades)
  List<String> listSpecialty;

  // Técnicas culinarias que maneja
  List<CulinaryTechniques> listCulinaryTechniques;

  // Cuantos años tiene cocinando
  int experience;

  // Rol basado en la puntuación
  String? role;

  // Puntuación del cocinero basada en recetas
  int? punctuation;

  // Lista de recetas elaboradas o creadas por este cocinero
  List<Recipe>? listRecipes;

  // Lista de recetas favoritas de este cocinero
  List<Recipe>? listRecipesFavorites;

  // Imagen del perfil
  String imagePerfilBase64;

  // Imagen o imágenes adicionales del cocinero en base64
  List<String>? imagesCookBase64;

  // Fecha de creación del perfil
  DateTime? createDate;

  // Fecha de actualización del perfil
  DateTime? updateDate;

  // Constructor
  Cook({
    required this.firstName,
    required this.lastName,
    required this.nickName,
    required this.username,
    required this.birthDate,
    required this.city,
    required this.country,
    required this.password,
    required this.listSpecialty,
    required this.listCulinaryTechniques,
    required this.experience,
    required this.imagePerfilBase64
  });

  // Método para convertir de JSON a objeto Dart
  factory Cook.fromJson(Map<String, dynamic> json) {
    return Cook(
      firstName: json['firstName'],
      lastName: json['lastName'],
      nickName: json['nickName'],
      username: json['username'],
      birthDate: DateTime.parse(json['birthDate']),
      city: json['city'],
      country: json['country'],
      password: json['password'],
      listSpecialty: List<String>.from(json['listSpecialty']),
      listCulinaryTechniques: (json['listCulinaryTechniques'] as List)
          .map((e) => CulinaryTechniques.fromJson(e))
          .toList(),
      experience: json['experience'],
      imagePerfilBase64: json['imagePerfilBase64'],
    );
  }

  // Método para convertir de objeto Dart a JSON
  Map<String, dynamic> toJson() {
    return {
      'firstName': firstName,
      'lastName': lastName,
      'nickName': nickName,
      'username': username,
      'birthDate': birthDate.toIso8601String(),
      'city': city,
      'country': country,
      'password': password,
      'listSpecialty': listSpecialty,
      'listCulinaryTechniques': listCulinaryTechniques
          .map((e) => e.toJson())
          .toList(),
      'experience': experience,
      'imagePerfilBase64': imagePerfilBase64,
    };
  }
}
