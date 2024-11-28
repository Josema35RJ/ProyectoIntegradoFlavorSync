import 'dart:convert';
import 'dart:io';

import 'package:country_picker/country_picker.dart';
import 'package:flavorsync/models/cook.dart';
import 'package:flavorsync/models/culinaryTechniques.dart';
import 'package:flavorsync/presentation/login_screen.dart';
import 'package:flavorsync/services/login.service.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:multi_select_flutter/multi_select_flutter.dart';

class RegisterScreen extends StatefulWidget {
  static const String name = 'register_screen';
  const RegisterScreen({Key? key}) : super(key: key);

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _formKey = GlobalKey<FormState>();

  // Controladores para los campos de entrada
  final TextEditingController _firstNameController = TextEditingController();
  final TextEditingController _lastNameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController = TextEditingController();
  final TextEditingController _nicknameController = TextEditingController();
  final TextEditingController _birthDateController = TextEditingController();
  final TextEditingController _experienceController = TextEditingController();

  // Variables para país y ciudad
  Country? _selectedCountry;
  String? _selectedCity;
  List<String> _cities = [];
  bool _isLoadingCities = false;

  // Imagen seleccionada
  File? _selectedImage;
  final ImagePicker _imagePicker = ImagePicker();

final List<String> _specialties = [
  "Panadería",
  "Repostería",
  "Gastronomía Internacional",
  "Comida Vegana",
  "Carnes",
  "Aves",
  "Pescados",
  "Mariscos",
  "Vegetariano",
  "Vegano",
  "Pastelería",
  "Postres",
  "Sopas",
  "Cremas",
  "Ensaladas",
  "Entrantes",
  "Tapas",
  "Bebidas",
  "Coctelería",
  "Arroces",
  "Pasta",
  "Pizza",
  "Cocina Internacional",
  "Cocina Mexicana",
  "Cocina Italiana",
  "Cocina Japonesa",
  "Cocina China",
  "Cocina India",
  "Cocina Árabe",
  "Cocina Francesa",
  "Cocina Española",
  "Cocina Peruana",
  "Cocina Mediterránea",
  "Barbacoas",
  "Asados",
  "Guisos",
  "Currys",
  "Ceviches",
  "Helados",
  "Dulces y Golosinas",
  "Quesos y Embutidos",
  "Dips y Salsas"
];

  final Future<List> _favoriteTechniques = UserService().fetchCulinaryTechniques();
  List<String> _selectedSpecialties = [];
  List<CulinaryTechniques> _selectedTechniques = [];

  @override
  void initState() {
    super.initState();
  }

  Future<void> _selectImage(ImageSource source) async {
    try {
      final pickedFile = await _imagePicker.pickImage(source: source);
      if (pickedFile != null) {
        setState(() {
          _selectedImage = File(pickedFile.path);
        });
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al seleccionar la imagen: $e')),
      );
    }
  }

  Future<void> _getCitiesForCountry() async {
    if (_selectedCountry == null) return;

    setState(() {
      _isLoadingCities = true;
    });

    final apiKey = 'josemanuelrj.'; // Cambiar por tu clave de API
    final countryCode = _selectedCountry!.countryCode;

    try {
      final response = await http.get(Uri.parse(
        'http://api.geonames.org/searchJSON?country=$countryCode&featureClass=P&maxRows=10&username=$apiKey',
      ));

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        final cityNames = (data['geonames'] as List)
            .map((city) => city['name'] as String)
            .toList();

        setState(() {
          _cities = cityNames;
          _selectedCity = _cities.isNotEmpty ? _cities.first : null;
        });
      } else {
        throw Exception('Error al obtener ciudades: ${response.body}');
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error al obtener ciudades: $e')),
      );
    } finally {
      setState(() {
        _isLoadingCities = false;
      });
    }
  }

  Future<void> _register() async {
    if (!_formKey.currentState!.validate()) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Por favor completa todos los campos correctamente')),
      );
      return;
    }

    String? imageBase64;
    if (_selectedImage != null) {
      final bytes = await _selectedImage!.readAsBytes();
      imageBase64 = base64Encode(bytes);
    }

    final culinaryTechniques = _selectedTechniques
;

    final cook = Cook(
      firstName: _firstNameController.text,
      lastName: _lastNameController.text,
      nickName: _nicknameController.text,
      username: _emailController.text,
      birthDate: DateTime.parse(_birthDateController.text),
      city: _selectedCity ?? '',
      country: _selectedCountry?.name ?? '',
      password: _passwordController.text,
      listSpecialty: _selectedSpecialties,
      listCulinaryTechniques: culinaryTechniques,
      experience: int.parse(_experienceController.text),
      imagePerfilBase64: imageBase64 ?? '',
    );

    final String? loginResult = await UserService().register(cook);
    if (loginResult == 'success') {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('¡Registro exitoso para ${_firstNameController.text}!')),
      );
      context.pushNamed(LoginScreen.name);
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Error al registrar al usuario')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          ClipPath(
            clipper: BottomRoundedAppBarClipper(),
            child: Container(
              color: Colors.brown.shade800,
              height: 120,
              alignment: Alignment.bottomCenter,
              padding: const EdgeInsets.only(bottom: 16),
              child: const Text(
                'Regístrate en FlavorSync',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
            ),
          ),
          Expanded(
            child: Center(
              child: Padding(
                padding: const EdgeInsets.all(16.0),
                child: Container(
                  constraints: const BoxConstraints(maxWidth: 400),
                  decoration: BoxDecoration(
                    color: Colors.brown.shade50,
                    borderRadius: BorderRadius.circular(24),
                    boxShadow: [
                      BoxShadow(
                        color: Colors.brown.shade200,
                        blurRadius: 10,
                        offset: const Offset(0, 5),
                      ),
                    ],
                  ),
                  padding: const EdgeInsets.all(24),
                  child: SingleChildScrollView(
                    child: Form(
                      key: _formKey,
                      child: Column(
                        children: [
                          GestureDetector(
                            onTap: () => _showImageSourceDialog(),
                            child: CircleAvatar(
                              radius: 50,
                              backgroundImage: _selectedImage != null
                                  ? FileImage(_selectedImage!)
                                  : const AssetImage('assets/images/placeholder.png') as ImageProvider,
                              child: _selectedImage == null
                                  ? const Icon(Icons.add_a_photo, size: 32, color: Colors.white)
                                  : null,
                            ),
                          ),
                          const SizedBox(height: 16),
                          _buildTextField('Nombre', _firstNameController, Icons.person),
                          const SizedBox(height: 16),
                          _buildTextField('Apellido', _lastNameController, Icons.person_outline),
                          const SizedBox(height: 16),
                          _buildTextField('Correo Electrónico', _emailController, Icons.email,
                              keyboardType: TextInputType.emailAddress),
                          const SizedBox(height: 16),
                          _buildTextField('Nickname', _nicknameController, Icons.person_pin),
                          const SizedBox(height: 16),
                          _buildDatePickerField(),
                          const SizedBox(height: 16),
                          _buildTextField('Experiencia (años)', _experienceController, Icons.star,
                              keyboardType: TextInputType.number),
                          const SizedBox(height: 16),
                          _buildTextField('Contraseña', _passwordController, Icons.lock,
                              obscureText: true),
                          const SizedBox(height: 16),
                          _buildTextField('Confirmar Contraseña', _confirmPasswordController,
                              Icons.lock_outline,
                              obscureText: true),
                          const SizedBox(height: 16),
                          _buildCountryAndCitySelectors(),
                          const SizedBox(height: 16),
                          _buildMultiSelect('Especialidades', _specialties, _selectedSpecialties),
                          const SizedBox(height: 16),
                          _buildMultiSelectTechniques('Técnicas Favoritas', _favoriteTechniques, _selectedTechniques),
                          const SizedBox(height: 16),
                          ElevatedButton(
                            onPressed: _register,
                            style: ElevatedButton.styleFrom(
                              backgroundColor: Colors.brown.shade800,
                              padding: const EdgeInsets.symmetric(vertical: 12, horizontal: 100),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(30),
                              ),
                            ),
                            child: const Text(
                              'Registrar',
                              style: TextStyle(fontSize: 16),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTextField(String label, TextEditingController controller, IconData icon,
      {bool obscureText = false, TextInputType keyboardType = TextInputType.text}) {
    return TextFormField(
      controller: controller,
      decoration: InputDecoration(
        labelText: label,
        labelStyle: TextStyle(color: Colors.brown.shade800),
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
        filled: true,
        fillColor: Colors.white,
        prefixIcon: Icon(icon, color: Colors.brown.shade800),
      ),
      obscureText: obscureText,
      keyboardType: keyboardType,
      validator: (value) {
        if (value == null || value.isEmpty) {
          return 'Por favor ingresa $label';
        }
        return null;
      },
    );
  }

  Widget _buildDatePickerField() {
    return TextFormField(
      controller: _birthDateController,
      decoration: InputDecoration(
        labelText: 'Cumpleaños',
        labelStyle: TextStyle(color: Colors.brown.shade800),
        border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
        filled: true,
        fillColor: Colors.white,
        prefixIcon: const Icon(Icons.calendar_today, color: Colors.brown),
      ),
      readOnly: true,
      onTap: () async {
        final DateTime? pickedDate = await showDatePicker(
          context: context,
          initialDate: DateTime.now(),
          firstDate: DateTime(1900),
          lastDate: DateTime(2100),
        );
        if (pickedDate != null) {
          setState(() {
            _birthDateController.text = pickedDate.toLocal().toString().split(' ')[0];
          });
        }
      },
    );
  }

  Widget _buildCountryAndCitySelectors() {
    return Column(
      children: [
        InputDecorator(
          decoration: InputDecoration(
            labelText: 'País',
            labelStyle: TextStyle(color: Colors.brown.shade800),
            border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
            filled: true,
            fillColor: Colors.white,
          ),
          child: InkWell(
            onTap: () => showCountryPicker(
              context: context,
              onSelect: (Country country) {
                setState(() {
                  _selectedCountry = country;
                  _getCitiesForCountry();
                });
              },
            ),
            child: Text(
              _selectedCountry?.name ?? 'Seleccionar país',
              style: TextStyle(color: Colors.brown.shade800, fontSize: 16),
            ),
          ),
        ),
        const SizedBox(height: 8),
        if (_isLoadingCities)
          const CircularProgressIndicator()
        else
          DropdownButtonFormField<String>(
            value: _selectedCity,
            items: _cities
                .map((city) => DropdownMenuItem(value: city, child: Text(city)))
                .toList(),
            onChanged: (value) {
              setState(() {
                _selectedCity = value;
              });
            },
            decoration: InputDecoration(
              labelText: 'Ciudad',
              labelStyle: TextStyle(color: Colors.brown.shade800),
              border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
              filled: true,
              fillColor: Colors.white,
              prefixIcon: const Icon(Icons.location_city, color: Colors.brown),
            ),
          ),
      ],
    );
  }

  Widget _buildMultiSelect(String label, List<String> items, List<String> selectedItems) {
    return MultiSelectDialogField(
      title: Text(label),
      items: items.map((item) => MultiSelectItem(item, item)).toList(),
      initialValue: selectedItems,
      onConfirm: (values) {
        setState(() {
          selectedItems.clear();
          selectedItems.addAll(values.cast<String>());
        });
      },
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(12),
        border: Border.all(color: Colors.brown.shade800),
      ),
    );
  }

Widget _buildMultiSelectTechniques(String label, Future<List> itemsFuture, List<CulinaryTechniques> selectedItems) {
  return FutureBuilder<List>(
    future: itemsFuture, // El Future que obtiene la lista de elementos
    builder: (context, snapshot) {
      // Si el Future aún está cargando
      if (snapshot.connectionState == ConnectionState.waiting) {
        return CircularProgressIndicator(); // Muestra un indicador de carga mientras se espera
      }

      // Si ocurre un error al obtener los datos
      if (snapshot.hasError) {
        return Text('Error: ${snapshot.error}');
      }

      // Si los datos se han obtenido correctamente
      if (snapshot.hasData) {
        List items = snapshot.data!; // Accede a los datos obtenidos

        return MultiSelectDialogField(
          title: Text(label),
          items: items.map((item) => MultiSelectItem(item, item.name)).toList(),
          initialValue: selectedItems,
          onConfirm: (values) {
            setState(() {
              selectedItems.clear();
           selectedItems.addAll(values.map((e) => e as CulinaryTechniques));
            });
          },
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(12),
            border: Border.all(color: Colors.brown.shade800),
          ),
        );
      } else {
        return Text('No hay datos disponibles');
      }
    },
  );
}

  void _showImageSourceDialog() {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Selecciona una fuente de imagen'),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
                _selectImage(ImageSource.gallery);
              },
              child: const Text('Galería'),
            ),
            TextButton(
              onPressed: () {
                Navigator.pop(context);
                _selectImage(ImageSource.camera);
              },
              child: const Text('Cámara'),
            ),
          ],
        );
      },
    );
  }
}
