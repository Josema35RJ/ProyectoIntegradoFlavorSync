import 'package:flavorsync/models/recipe.dart';
import 'package:flutter/material.dart';

class RecipeDetailScreen extends StatelessWidget {
  static const String name = 'recipeDetails_screen';

  final Recipe recipe;

  const RecipeDetailScreen({Key? key, required this.recipe}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery.of(context).size;

    return Scaffold(
      body: CustomScrollView(
        slivers: [
          // AppBar redondeado con Material 3
          SliverAppBar(
            expandedHeight: screenSize.height * 0.35,
            backgroundColor: Colors.brown.shade800,
            pinned: true, // Fija el AppBar al hacer scroll
            flexibleSpace: FlexibleSpaceBar(
              title: Text(
                recipe.name,
                style: const TextStyle(
                  color: Colors.white,
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              background: recipe.imageRecipePerfil != null
                  ? Image.memory(
                      recipe.imageRecipePerfil!,
                      fit: BoxFit.cover,
                    )
                  : const SizedBox(),
            ),
            shape: const RoundedRectangleBorder(
              borderRadius: BorderRadius.only(
                bottomLeft: Radius.circular(30),
                bottomRight: Radius.circular(30),
              ),
            ),
          ),

          SliverList(
            delegate: SliverChildListDelegate(
              [
                const SizedBox(height: 20),
                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _InfoCard(
                    items: [
                      _InfoItem(title: 'Nivel:', value: recipe.level ?? 'N/A'),
                      _InfoItem(title: 'Tiempo:', value: '${recipe.preparationTime} min'),
                      _InfoItem(title: 'Porciones:', value: '${recipe.diners} personas'),
                      _InfoItem(title: 'Dificultad:', value: recipe.difficulty ?? 'N/A'),
                    ],
                  ),
                ),

                const SizedBox(height: 20),

                // Sección de imágenes de la receta
                if (recipe.imagesRecipe.isNotEmpty)
                  FractionallySizedBox(
                    widthFactor: 0.9,
                    child: _SectionCard(
                      title: 'Imágenes de la Receta',
                      children: [
                        SizedBox(
                          height: 200,
                          child: ListView.builder(
                            scrollDirection: Axis.horizontal,
                            itemCount: recipe.imagesRecipe.length,
                            itemBuilder: (context, index) {
                              return Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: Image.memory(
                                  recipe.imagesRecipe[index],
                                  fit: BoxFit.cover,
                                  width: 150,
                                  height: 150,
                                ),
                              );
                            },
                          ),
                        ),
                      ],
                    ),
                  ),

                const SizedBox(height: 20),

                if (recipe.history != null)
                  FractionallySizedBox(
                    widthFactor: 0.9,
                    child: _SectionCard(
                      title: 'Historia de la Receta',
                      children: [
                        Text(
                          recipe.history!,
                          style: const TextStyle(fontSize: 16),
                        ),
                      ],
                    ),
                  ),

                const SizedBox(height: 20),

                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _SectionCard(
                    title: 'Ingredientes',
                    children: recipe.ingredients
                        .map((ingredient) =>
                            Text('• $ingredient', style: const TextStyle(fontSize: 16)))
                        .toList(),
                  ),
                ),

                const SizedBox(height: 20),

                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _SectionCard(
                    title: 'Utensilios Necesarios',
                    children: recipe.listkitchenUtensils
                        .map((utensil) =>
                            Text('• $utensil', style: const TextStyle(fontSize: 16)))
                        .toList(),
                  ),
                ),

                const SizedBox(height: 20),

                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _SectionCard(
                    title: 'Técnicas Culinarias',
                    children: recipe.listRecipeTechniques
                        .map((technique) => Text('• ${technique.name}',
                            style: const TextStyle(fontSize: 16)))
                        .toList(),
                  ),
                ),

                const SizedBox(height: 20),

                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _SectionCard(
                    title: 'Restricciones Dietéticas y Alergias',
                    children: recipe.allergensAndDietaryRestrictions
                        .map((restriction) =>
                            Text('• $restriction', style: const TextStyle(fontSize: 16)))
                        .toList(),
                  ),
                ),

                const SizedBox(height: 20),

                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _SectionCard(
                    title: 'Instrucciones',
                    children: [
                      Text(recipe.instructions, style: const TextStyle(fontSize: 16)),
                    ],
                  ),
                ),

                const SizedBox(height: 20),

                // Puntuación de la receta en estrellas
                FractionallySizedBox(
                  widthFactor: 0.9,
                  child: _SectionCard(
                    title: 'Puntuación de la Receta',
                    children: [
                      _buildRatingStars(recipe.averageRating),
                    ],
                  ),
                ),

                const SizedBox(height: 20),

                // Sección de comentarios
                if (recipe.listComments.isNotEmpty)
                  FractionallySizedBox(
                    widthFactor: 0.9,
                    child: _SectionCard(
                      title: 'Comentarios',
                      children: recipe.listComments
                          .map((comment) => Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text(
                                    '- ${comment.description} (${comment.cookId}) (${comment.createDate})',
                                    style: const TextStyle(fontSize: 16),
                                  ),
                                  SizedBox(height: 5),
                                  _buildRatingStarsComment(comment.punctuation), // Estrellas en comentario
                                  SizedBox(height: 10),
                                ],
                              ))
                          .toList(),
                    ),
                  ),

                const SizedBox(height: 20),

                if (recipe.nutritionalInformation != null)
                  FractionallySizedBox(
                    widthFactor: 0.9,
                    child: _SectionCard(
                      title: 'Información Nutricional',
                      children: [
                        Text(
                            'Calorías: ${recipe.nutritionalInformation?.calories ?? 'N/A'}',
                            style: const TextStyle(fontSize: 16)),
                        Text(
                            'Proteínas: ${recipe.nutritionalInformation?.proteins ?? 'N/A'}',
                            style: const TextStyle(fontSize: 16)),
                        Text(
                            'Carbohidratos: ${recipe.nutritionalInformation?.carbohydrates ?? 'N/A'}',
                            style: const TextStyle(fontSize: 16)),
                        Text(
                            'Grasas: ${recipe.nutritionalInformation?.fat ?? 'N/A'}',
                            style: const TextStyle(fontSize: 16)),
                      ],
                    ),
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  // Función para construir las estrellas según la puntuación de la receta
  Widget _buildRatingStars(double punctuation) {
    int fullStars = punctuation.toInt();
    int emptyStars = 5 - fullStars;

    List<Widget> stars = [];

    // Añadir estrellas llenas
    for (int i = 0; i < fullStars; i++) {
      stars.add(const Icon(Icons.star, color: Colors.amber));
    }

    // Añadir estrellas vacías
    for (int i = 0; i < emptyStars; i++) {
      stars.add(const Icon(Icons.star_border, color: Colors.amber));
    }

    return Row(children: stars);
  }
}


  // Función para construir las estrellas según la puntuación de la receta
  Widget _buildRatingStarsComment(int punctuation) {
    int fullStars = punctuation.toInt();
    int emptyStars = 5 - fullStars;

    List<Widget> stars = [];

    // Añadir estrellas llenas
    for (int i = 0; i < fullStars; i++) {
      stars.add(const Icon(Icons.star, color: Colors.amber));
    }

    // Añadir estrellas vacías
    for (int i = 0; i < emptyStars; i++) {
      stars.add(const Icon(Icons.star_border, color: Colors.amber));
    }

    return Row(children: stars);
  }


// Widgets auxiliares

class _InfoCard extends StatelessWidget {
  final List<_InfoItem> items;

  const _InfoCard({required this.items});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(20),
        boxShadow: const [
          BoxShadow(color: Colors.black26, blurRadius: 5, offset: Offset(0, 3)),
        ],
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: items,
      ),
    );
  }
}

class _InfoItem extends StatelessWidget {
  final String title;
  final String value;

  const _InfoItem({required this.title, required this.value});

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(title, style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
        Text(value, style: const TextStyle(fontSize: 14)),
      ],
    );
  }
}

class _SectionCard extends StatelessWidget {
  final String title;
  final List<Widget> children;

  const _SectionCard({required this.title, required this.children});

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(20),
        boxShadow: const [
          BoxShadow(color: Colors.black26, blurRadius: 5, offset: Offset(0, 3)),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            title,
            style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold, color: Colors.brown),
          ),
          const SizedBox(height: 10),
          ...children,
        ],
      ),
    );
  }
}
