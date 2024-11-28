import 'package:flavorsync/models/recipe.dart';
import 'package:flavorsync/presentation/cook/home_screen.dart';
import 'package:flavorsync/presentation/cook/perfil_screen.dart';
import 'package:flavorsync/presentation/cook/viewRecipe_screen.dart';
import 'package:flavorsync/presentation/login_screen.dart';
import 'package:flavorsync/presentation/register_screen.dart';
import 'package:go_router/go_router.dart';

final appRouter = GoRouter(
  initialLocation: '/auth/login',
  routes: [
    GoRoute(
        path: '/auth/login',
        name: LoginScreen.name,
        builder: (context, state) => const LoginScreen()),
    GoRoute(
        path: '/auth/register',
        name: RegisterScreen.name,
        builder: (context, state) => const RegisterScreen()),
    GoRoute(
        path: '/auth/home',
        name: HomeScreen.name,
        builder: (context, state) => const HomeScreen()),
    GoRoute(
      path: '/auth/ViewRecipe',
      name: RecipeDetailScreen.name,
      builder: (context, state) {
        final receta = state.extra as Recipe; // Obtener la receta desde `extra`
        return RecipeDetailScreen(recipe: receta);
      },
    ),
    GoRoute(
      path: '/auth/CookPerfil',
      name: CookProfileScreen.name,
      builder: (context, state) {
        return CookProfileScreen();
      },
    )
  ],
);
