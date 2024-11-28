import 'dart:convert';

import 'package:flavorsync/models/cook.dart';
import 'package:flavorsync/services/login.service.dart';
import 'package:flutter/material.dart';

class CookProfileScreen extends StatefulWidget {
  static const String name = 'cookPerfil_screen';

  const CookProfileScreen({Key? key}) : super(key: key);

  @override
  _CookProfileScreenState createState() => _CookProfileScreenState();
}

class _CookProfileScreenState extends State<CookProfileScreen> {
  // Cambiado el tipo de 'cookProfileFuture' para ser un 'Future<Cook?>'
  late Future<Cook?> cookProfileFuture;

  @override
  void initState() {
    super.initState();
    _loadCookProfile();
  }

  void _loadCookProfile() {
    cookProfileFuture = UserService().fetchCookData();
  }

  int _calculateAge(DateTime birthDate) {
    final today = DateTime.now();
    int age = today.year - birthDate.year;
    if (today.month < birthDate.month ||
        (today.month == birthDate.month && today.day < birthDate.day)) {
      age--;
    }
    return age;
  }

  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery.of(context).size;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Perfil del Cocinero'),
        backgroundColor: Colors.brown.shade800,
        elevation: 4,
      ),
      body: FutureBuilder<Cook?>(
        future: cookProfileFuture, // Usamos 'cookProfileFuture' aquí
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error al cargar el perfil: ${snapshot.error}'));
          } else if (snapshot.hasData) {
            final cook = snapshot.data!; // Asegúrate de que los datos son correctos
            return SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  // Imagen de perfil
                  Stack(
                    children: [
                      Container(
                        width: screenSize.width,
                        height: screenSize.height * 0.35,
                        decoration: BoxDecoration(
                          image: DecorationImage(
                            image: MemoryImage(base64Decode(cook.imagePerfilBase64)),
                            fit: BoxFit.cover,
                          ),
                        ),
                      ),
                      Positioned(
                        bottom: 16,
                        left: 16,
                        child: Container(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                          decoration: BoxDecoration(
                            color: Colors.black.withOpacity(0.6),
                            borderRadius: BorderRadius.circular(8),
                          ),
                          child: Text(
                            '${cook.firstName} ${cook.lastName}',
                            style: const TextStyle(
                              color: Colors.white,
                              fontWeight: FontWeight.bold,
                              fontSize: 24,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                  const SizedBox(height: 20),
                  Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 16.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        // Apodo
                        Text(
                          '@${cook.nickName}',
                          style: TextStyle(
                            fontSize: 18,
                            color: Colors.brown.shade600,
                          ),
                        ),
                        const SizedBox(height: 16),

                        // Información personal
                        Card(
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                          elevation: 3,
                          margin: const EdgeInsets.only(bottom: 16),
                          child: Padding(
                            padding: const EdgeInsets.all(16.0),
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                ListTile(
                                  leading: const Icon(Icons.location_on, color: Colors.brown),
                                  title: Text('${cook.city}, ${cook.country}'),
                                ),
                                ListTile(
                                  leading: const Icon(Icons.cake, color: Colors.brown),
                                  title: Text('${_calculateAge(cook.birthDate)} años'),
                                ),
                                ListTile(
                                  leading: const Icon(Icons.star, color: Colors.brown),
                                  title: Text('${cook.experience} años de experiencia'),
                                ),
                              ],
                            ),
                          ),
                        ),

                        // Especialidades
                        const Text(
                          'Especialidades: ',
                          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 8),
                        Wrap(
                          spacing: 8.0,
                          runSpacing: 4.0,
                          children: cook.listSpecialty
                              .map((specialty) => Chip(
                                    label: Text(specialty),
                                    backgroundColor: Colors.brown.shade100,
                                  ))
                              .toList(),
                        ),
                        const SizedBox(height: 20),

                        // Técnicas culinarias
                        const Text(
                          'Técnicas culinarias: ',
                          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 8),
                        Wrap(
                          spacing: 8.0,
                          runSpacing: 4.0,
                          children: cook.listCulinaryTechniques
                              .map((technique) => Chip(
                                    label: Text(technique.name),
                                    backgroundColor: Colors.brown.shade100,
                                  ))
                              .toList(),
                        ),
                        const SizedBox(height: 20),

                        // Botones de acción
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            ElevatedButton.icon(
                              onPressed: () {},
                              icon: const Icon(Icons.person_add),
                              label: const Text('Seguir'),
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.brown.shade800,
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(12),
                                ),
                              ),
                            ),
                            ElevatedButton.icon(
                              onPressed: () {},
                              icon: const Icon(Icons.book),
                              label: const Text('Ver Recetas'),
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.brown.shade800,
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(12),
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            );
          } else {
            return const Center(child: Text('No se encontró el perfil del cocinero.'));
          }
        },
      ),
    );
  }
}
