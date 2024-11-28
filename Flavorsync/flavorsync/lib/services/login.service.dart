import 'dart:convert';

import 'package:flavorsync/models/cook.dart';
import 'package:flavorsync/models/culinaryTechniques.dart';
import 'package:flavorsync/models/recipe.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;

class UserService extends ChangeNotifier {
  final String baseURL = 'https://proyectointegradoflavorsync.onrender.com/api';
  final storage = const FlutterSecureStorage();

  static String userEmail = '';
  static String userId = '' ;
  static String userRole = '';

  bool isLoading = true;
  static Cook? cook;

  Future<String> register(Cook cook) async {
    final url = Uri.parse('$baseURL/register');
    try {
      final response = await http.post(
        url,
        headers: {
          'Content-type': 'application/json',
          'Accept': 'application/json',
        },
        body: json.encode(cook.toJson()),
         
      );
      final Map<String, dynamic> decoded = json.decode(response.body);
    
      if (decoded['success'] == true) {
        return 'success';
      } else {
        return handleRegisterError(decoded);
      }
    } catch (e) {
      return 'Error al conectar con el servidor: $e';
    }
  }

  String handleRegisterError(Map<String, dynamic> decoded) {
    if (decoded['message'] == "El correo electrónico ya está registrado") {
      return 'El correo electrónico ya está registrado';
    } else {
      return 'Error: ${decoded['message']}';
    }
  }

  String handleLoginError(Map<String, dynamic> decoded) {
    if (decoded['message'] == "Usuario o clave incorrectos") {
      return 'Usuario o contraseña incorrectos';
    } else if (decoded['message'] == "Error al iniciar sesion") {
      return 'Usuario o contraseña incorrectos';
    } else if (decoded['message'] == "El usuario no esta activado") {
      return 'El usuario no esta activado';
    } else if (decoded['message'] == "El usuario ha sido borrado") {
      return 'El usuario ha sido borrado';
    } else {
      return decoded['message'];
    }
  }

  String handleGetClassesError(Map<String, dynamic> decoded) {
    if (decoded['message'] == "No tienes permiso para ver estos servicios") {
      return 'No tienes permiso para ver estos servicios';
    } else {
      return 'Error: ${decoded['message']}';
    }
  }

  Future<String?> login(String username, String password) async {
    final url = Uri.parse('$baseURL/login');

    try {
      // Crear el cuerpo de la solicitud en formato x-www-form-urlencoded
      final body = {
        'username': username,
        'password': password,
      };

      // Convertir el cuerpo al formato adecuado
      final encodedBody = body.entries.map((entry) {
        return '${Uri.encodeQueryComponent(entry.key)}=${Uri.encodeQueryComponent(entry.value)}';
      }).join('&');

      final response = await http.post(
        url,
        headers: {
          'Content-type': 'application/x-www-form-urlencoded', // Cambiar el tipo de contenido
          'Accept': 'application/json',
        },
        body: encodedBody, // Enviar el cuerpo codificado
      );

      // Decodificar la respuesta
      final Map<String, dynamic> decoded = json.decode(response.body);

      if ( decoded['success']== true) {
        UserService.userId = decoded['data']['id'].toString();
        UserService.userEmail = username;
        UserService.userRole = decoded['data']['ROL'];

        // Guardar el token y el id en almacenamiento seguro
        await storage.write(key: 'token', value: decoded['data']['token']);
        await storage.write(key: 'id', value: decoded['data']['id'].toString());
        return 'success';
      } else {
        return handleLoginError(decoded);
      }
    } catch (e) {
      return 'Error al conectar con el servidor: $e';
    }
  }

   Future<List<CulinaryTechniques>> fetchCulinaryTechniques() async {
  final url = Uri.parse('$baseURL/ListCulinaryTechniques');
  
  try {
    final response = await http.get(url);

    if (response.statusCode == 200) {
      // Use utf8.decode() if the response body is not in utf8 by default
      final decodedResponse = utf8.decode(response.bodyBytes);
      final Map<String, dynamic> decoded = json.decode(decodedResponse);

      if (decoded['success'] == true) {
        List<CulinaryTechniques> culinaryTechniques = [];
        
        for (var item in decoded['data']) {
          culinaryTechniques.add(CulinaryTechniques.fromJson(item));
        }
 
        return culinaryTechniques;
      } else {
       
        throw Exception('Error: ${decoded['message']}');
      }
    } else {
      throw Exception('Failed to load culinary techniques');
    }
  } catch (e) {
    throw Exception('Error al conectar con el servidor: $e');
  }
}

Future<List<Recipe>> fetchRecipes() async {
  final url = Uri.parse('$baseURL/auth/cookapp/ListRecipe');

  try {
    final response = await http.get(
      url,
      headers: {
        'Content-type': 'application/json',
        'Accept': 'application/json',
         'Authorization': 'Bearer ${await storage.read(key: 'token')}',
      },
    );

    if (response.statusCode == 200) {
      final Map<String, dynamic> decoded = json.decode(utf8.decode(response.bodyBytes));
       
     
        if (decoded['success'] == true) {
          // Procesar datos de recetas
          return (decoded['data'] as List)
              .map((item) {
                // Validar que cada elemento tenga los campos necesarios
                try {
                  return Recipe.fromJson(item);
                } catch (e) {
                  print('Error al procesar receta: $e\nDatos: $item');
                  return null; // Ignorar recetas mal formadas
                }
              })
              .where((recipe) => recipe != null) // Filtrar valores nulos
              .cast<Recipe>()
              .toList();
        }  else {
        throw Exception('Error: ${decoded['message']}');
      }
    } else {
      throw Exception('Failed to load recipes');
    }
  } catch (e) {
   
        throw Exception('Error al conectar con el servidor: $e');
  }
}

 Future<Cook?> fetchCookData() async {
  final url = Uri.parse('$baseURL/auth/cookapp/Cook');

  try {
    final response = await http.post(
      url,
      headers: {
        'Content-type': 'application/json',
        'Authorization': 'Bearer ${await storage.read(key: 'token')}',
      },
      body: json.encode({
        "id": userId,
      }),
    );

    if (response.statusCode == 200) {
      // Primero decodificamos el cuerpo de la respuesta en UTF-8
      String decodedBody = utf8.decode(response.bodyBytes);

      // Ahora podemos parsear la respuesta JSON
      final Map<String, dynamic> decoded = json.decode(decodedBody);

      // Verificamos si la respuesta es exitosa
      if (decoded['success'] == true) {
        return Cook.fromJson(decoded);
      } else {
        print('Error desde el servidor: ${decoded['message']}');
      }
    } else {
      print('Error: Código de estado ${response.statusCode}');
    }
  } catch (e) {
    print('Error al conectar con el servidor: $e');
  }

  return null; // Return null if there's an error
}

}
