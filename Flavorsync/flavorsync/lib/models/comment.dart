import 'dart:convert';

class Comment {
  // Identificador único para cada comentario.
  int? id;

  // El comentario que escribe
  String description;

  // Puntuación que le da, al elaborarla el y probarla
  int punctuation;

  // Referencia al cocinero que escribió el comentario
  Cook? cookId;

  // Fecha de creación
  DateTime createDate;

  // Fecha de actualización
  DateTime? updateDate;

  // Relación recursiva: un comentario puede tener respuestas
  Comment? parentComment;

  // Respuestas a este comentario
  List<Comment> replies;

  // Constructor
  Comment({
    this.id,
    required this.description,
    required this.punctuation,
    this.cookId,
    required this.createDate,
    this.updateDate,
    this.parentComment,
    List<Comment>? replies,
  }) : replies = replies ?? [];

  // Factory constructor to create an instance from a map (for JSON parsing)
  factory Comment.fromMap(Map<String, dynamic> map) {
    return Comment(
      id: map['id'],
      description: map['description'],
      punctuation: map['punctuation'],
      cookId: map['cookId'] != null ? Cook.fromMap(map['cookId']) : null,
      createDate: DateTime.parse(map['createDate']),
      updateDate: map['updateDate'] != null ? DateTime.parse(map['updateDate']) : null,
      parentComment: map['parentComment'] != null ? Comment.fromMap(map['parentComment']) : null,
      replies: map['replies'] != null
          ? List<Comment>.from(map['replies']?.map((x) => Comment.fromMap(x)))
          : [],
    );
  }

  // Method to convert the instance to a map (for JSON serialization)
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'description': description,
      'punctuation': punctuation,
      'cookId': cookId?.toMap(),
      'createDate': createDate.toIso8601String(),
      'updateDate': updateDate?.toIso8601String(),
      'parentComment': parentComment?.toMap(),
      'replies': replies.map((x) => x.toMap()).toList(),
    };
  }

  // Converts the object to a JSON string
  String toJson() => jsonEncode(toMap());

  // Creates an instance from a JSON string
  factory Comment.fromJson(String source) => Comment.fromMap(jsonDecode(source));

  @override
  String toString() {
    return 'Comment(id: $id, description: $description, punctuation: $punctuation)';
  }
}

class Cook {
  int? id;
  String name;

  Cook({
    this.id,
    required this.name,
  });

  // Factory constructor to create an instance from a map (for JSON parsing)
  factory Cook.fromMap(Map<String, dynamic> map) {
    return Cook(
      id: map['id'],
      name: map['name'],
    );
  }

  // Method to convert the instance to a map (for JSON serialization)
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
    };
  }

  @override
  String toString() {
    return 'Cook(id: $id, name: $name)';
  }
}
