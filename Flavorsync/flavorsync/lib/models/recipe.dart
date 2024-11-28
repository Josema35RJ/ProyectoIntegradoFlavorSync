import 'dart:convert';
import 'dart:typed_data';

import 'package:flavorsync/models/comment.dart';
import 'package:flavorsync/models/culinaryTechniques.dart';
import 'package:flavorsync/models/nutritionalInformation.dart';

class Recipe {
  int? id;
  String name;
  String? level;
  int diners;
  double preparationTime;
  List<String> whereItisDone;
  List<String> category;
  double averageRating;
  List<Comment> listComments;
  List<String> listkitchenUtensils;
  List<CulinaryTechniques> listRecipeTechniques;
  String instructions;
  String? difficulty;
  List<String> allergensAndDietaryRestrictions;
  NutritionalInformation? nutritionalInformation;
  String? grades;
  String? history;
  String country;
  String city;
  List<Uint8List> imagesRecipe;
  Uint8List? imageRecipePerfil;
  List<String> ingredients;
  DateTime createDate;
  DateTime? updateDate;

  Recipe({
    this.id,
    required this.name,
    this.level,
    required this.diners,
    required this.preparationTime,
    required this.whereItisDone,
    required this.category,
    this.averageRating = 0,
    required this.listComments,
    required this.listkitchenUtensils,
    required this.listRecipeTechniques,
    required this.instructions,
    this.difficulty,
    required this.allergensAndDietaryRestrictions,
    this.nutritionalInformation,
    this.grades,
    this.history,
    required this.country,
    required this.city,
    required this.imagesRecipe,
    this.imageRecipePerfil,
    required this.ingredients,
    required this.createDate,
    this.updateDate,
  });

  factory Recipe.fromMap(Map<String, dynamic> map) {
    return Recipe(
      id: map['id'],
      name: map['name'],
      level: map['level'],
      diners: map['diners'],
      preparationTime: map['preparationTime'],
      whereItisDone: List<String>.from(map['whereItisDone']),
      category: List<String>.from(map['category']),
      averageRating: map['averageRating']?.toDouble() ?? 0,
      listComments: List<Comment>.from(map['listComments']?.map((x) => Comment.fromMap(x)) ?? []),
      listkitchenUtensils: List<String>.from(map['listkitchenUtensils'] ?? []),
      listRecipeTechniques: List<CulinaryTechniques>.from(map['listRecipeTechniques']?.map((x) => CulinaryTechniques.fromJson(x)) ?? []),
      instructions: map['instructions'],
      difficulty: map['difficulty'],
      allergensAndDietaryRestrictions: List<String>.from(map['allergensAndDietaryRestrictions'] ?? []),
      nutritionalInformation: map['nutritionalInformation'] != null ? NutritionalInformation.fromMap(map['nutritionalInformation']) : null,
      grades: map['grades'],
      history: map['history'],
      country: map['country'],
      city: map['city'],
      imagesRecipe: List<Uint8List>.from(map['imagesRecipe']?.map((x) => base64Decode(x)) ?? []),
      imageRecipePerfil: map['imageRecipePerfil'] != null ? base64Decode(map['imageRecipePerfil']) : null,
      ingredients: List<String>.from(map['ingredients'] ?? []),
      createDate: DateTime.parse(map['createDate']),
      updateDate: map['updateDate'] != null ? DateTime.parse(map['updateDate']) : null,
    );
  }

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'level': level,
      'diners': diners,
      'preparationTime': preparationTime,
      'whereItisDone': whereItisDone,
      'category': category,
      'averageRating': averageRating,
      'listComments': listComments.map((x) => x.toMap()).toList(),
      'listkitchenUtensils': listkitchenUtensils,
      'listRecipeTechniques': listRecipeTechniques.map((x) => x.toJson()).toList(),
      'instructions': instructions,
      'difficulty': difficulty,
      'allergensAndDietaryRestrictions': allergensAndDietaryRestrictions,
      'nutritionalInformation': nutritionalInformation?.toMap(),
      'grades': grades,
      'history': history,
      'country': country,
      'city': city,
      'imageRecipePerfil': imageRecipePerfil != null ? base64Encode(imageRecipePerfil!) : null,
      'imagesRecipe': imagesRecipe.isNotEmpty
          ? imagesRecipe.map((x) => base64Encode(x)).toList()
          : [],
      'ingredients': ingredients,
      'createDate': createDate.toIso8601String(),
      'updateDate': updateDate?.toIso8601String(),
    };
  }

  String toJson() => jsonEncode(toMap());

  factory Recipe.fromJson(String source) => Recipe.fromMap(jsonDecode(source));

  @override
  String toString() {
    return 'RecipeModel(id: $id, name: $name, diners: $diners, preparationTime: $preparationTime)';
  }
}
