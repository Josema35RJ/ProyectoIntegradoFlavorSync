import 'dart:typed_data';

import 'package:carousel_slider/carousel_slider.dart';
import 'package:flavorsync/models/recipe.dart';
import 'package:flavorsync/presentation/cook/perfil_screen.dart';
import 'package:flavorsync/presentation/cook/viewRecipe_screen.dart';
import 'package:flavorsync/services/login.service.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class HomeScreen extends StatefulWidget {
  static const String name = 'home_screen';
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late Future<List<Recipe>> recipesFuture;

  @override
  void initState() {
    super.initState();
    recipesFuture =
        UserService().fetchRecipes(); // Aquí se obtienen las recetas
  }

  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery.of(context).size;

    return SafeArea(
      child: Scaffold(
        appBar: PreferredSize(
          preferredSize: Size.fromHeight(kToolbarHeight),
          child: ClipRRect(
            borderRadius: const BorderRadius.only(
              bottomLeft: Radius.circular(30),
              bottomRight: Radius.circular(30),
            ),
            child: AppBar(
              backgroundColor: Colors.brown.shade800,
              elevation: 0,
              title: const Text(
                'Recetas de Cocina',
                style: TextStyle(
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                  fontSize: 22,
                ),
              ),
              actions: [
                IconButton(
                  icon: const Icon(Icons.person, size: 28),
                  onPressed: () {
                    context.pushNamed(CookProfileScreen.name);
                  },
                ),
              ],
            ),
          ),
        ),
        body: FutureBuilder<List<Recipe>>(
          future: recipesFuture,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const Center(child: CircularProgressIndicator());
            } else if (snapshot.hasError) {
              return Center(child: Text('Error: ${snapshot.error}'));
            } else if (snapshot.hasData) {
              final recetas = snapshot.data!;
              return SingleChildScrollView(
                child: Column(
                  children: [
                    // Añadir un SizedBox para crear espacio entre el AppBar y el Carousel
                    const SizedBox(
                        height:
                            20), // Aquí defines el espacio entre el navbar y el carrusel

                    // Carrusel con imágenes de las recetas
                    ConstrainedBox(
                      constraints: BoxConstraints(
                        maxHeight: screenSize.height * 0.35,
                      ),
                      child: CarouselSlider(
                        options: CarouselOptions(
                          autoPlay: true,
                          height: screenSize.height * 0.35,
                          viewportFraction: 1.0,
                          enlargeCenterPage: true,
                        ),
                        items: recetas.map((receta) {
                          return Builder(
                            builder: (BuildContext context) {
                              return Container(
                                margin:
                                    const EdgeInsets.symmetric(horizontal: 12),
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(16),
                                  boxShadow: [
                                    BoxShadow(
                                      color: Colors.black.withOpacity(0.1),
                                      blurRadius: 10,
                                      offset: Offset(0, 4),
                                    ),
                                  ],
                                ),
                                child: ClipRRect(
                                  borderRadius: BorderRadius.circular(16),
                                  child: Stack(
                                    children: [
                                      Image.memory(
                                        receta.imageRecipePerfil ??
                                            Uint8List(0),
                                        fit: BoxFit.cover,
                                        width: double.infinity,
                                        height: screenSize.height * 0.35,
                                      ),
                                      Positioned(
                                        bottom: screenSize.height * 0.02,
                                        left: 16,
                                        child: Container(
                                          padding: const EdgeInsets.symmetric(
                                              horizontal: 12, vertical: 6),
                                          decoration: BoxDecoration(
                                            color:
                                                Colors.black.withOpacity(0.5),
                                            borderRadius:
                                                BorderRadius.circular(8),
                                          ),
                                          child: Text(
                                            receta.name,
                                            style: const TextStyle(
                                              color: Colors.white,
                                              fontWeight: FontWeight.bold,
                                              fontSize: 18,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              );
                            },
                          );
                        }).toList(),
                      ),
                    ),
                    const SizedBox(height: 20),
                    // GridView de recetas
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 16),
                      child: GridView.builder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                          crossAxisCount: 2,
                          crossAxisSpacing: 10,
                          mainAxisSpacing: 10,
                          childAspectRatio: 0.7, // Proporción más baja
                        ),
                        itemCount: recetas.length,
                        itemBuilder: (context, index) {
                          return _buildRecipeCard(
                              recetas[index], context, screenSize);
                        },
                      ),
                    ),
                  ],
                ),
              );
            } else {
              return const Center(child: Text('No hay recetas disponibles.'));
            }
          },
        ),
      ),
    );
  }

  Widget _buildRecipeCard(
      Recipe receta, BuildContext context, Size screenSize) {
    String recipeImageUrl = receta.imagesRecipe.isNotEmpty
        ? receta.imagesRecipe[0].toString()
        : 'https://via.placeholder.com/150';

    return Card(
      elevation: 10,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20),
      ),
      child: ClipRRect(
        borderRadius: BorderRadius.circular(20),
        child: Column(
          children: [
            // Imagen de la receta alargada, ocupando más espacio
            Flexible(
              flex: 4, // Hacemos que la imagen ocupe más espacio
              child: SizedBox(
                width: double.infinity,
                child: receta.imagesRecipe.isNotEmpty
                    ? Image.memory(
                        receta.imagesRecipe[0],
                        fit: BoxFit
                            .cover, // Hacemos que la imagen ocupe toda el área
                      )
                    : Image.network(
                        recipeImageUrl,
                        fit: BoxFit.cover,
                      ),
              ),
            ),
            // Nombre de la receta
            Flexible(
              flex: 1,
              child: Padding(
                padding: const EdgeInsets.symmetric(vertical: 4.0),
                child: Text(
                  receta.name,
                  style: const TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.bold,
                  ),
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                  textAlign: TextAlign.center,
                ),
              ),
            ),
            // Botón para ver la receta, más pequeño y con estilo moderno
            Padding(
              padding:
                  const EdgeInsets.symmetric(vertical: 10.0, horizontal: 16.0),
              child: ElevatedButton(
                onPressed: () {
                  context.pushNamed(RecipeDetailScreen.name, extra: receta);
                },
                style: ElevatedButton.styleFrom(
                  backgroundColor: Colors.brown.shade800, // Modern color
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(
                        12), // Borde más pequeño y estilizado
                  ),
                  elevation: 5, // Efecto de sombra para darle profundidad
                  padding: EdgeInsets.symmetric(
                      vertical: 10.0,
                      horizontal: 20.0), // Botón más pequeño con padding
                  shadowColor:
                      Colors.black.withOpacity(0.3), // Sombra más sutil
                  minimumSize: Size(double.infinity,
                      45), // El botón tiene un tamaño más compacto
                ),
                child: const Text(
                  'Ver Receta',
                  style: TextStyle(
                    fontSize: 10, // Texto un poco más pequeño
                    fontWeight: FontWeight.bold,
                    letterSpacing: 1.0, // Espaciado de letras
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
