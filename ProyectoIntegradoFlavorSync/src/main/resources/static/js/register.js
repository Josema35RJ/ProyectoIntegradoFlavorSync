$(document)
	.ready(
		function() {
			$(
				"#firstName, #lastName, #username, #password, #confirmPassword, #dni, #postalCode, #province, #city, #weight, #height")
				.keyup(
					function() {
						var input = $(this);
						var is_name = input.val();
						if (is_name) {
							input
								.removeClass(
									"invalid")
								.addClass("valid")
								.removeClass(
									"invalid");
						} else {
							input
								.removeClass(
									"valid")
								.addClass("invalid");
						}
					});

			$("#dni").inputmask("99999999A");
			$("#postalCode").inputmask("99999");

			$.validator.addMethod("pwcheck", function(value) {
				return /^[A-Za-z0-9\d=!\-@._*]*$/.test(value) // consists of only these
					&& /[a-z]/.test(value) // has a lowercase letter
					&& /\d/.test(value) // has a digit
			});

			$("#registerForm")
				.validate(
					{
						rules: {
							firstName: "required",
							lastName: "required",
							username: {
								required: true,
								email: true
							},
							password: {
								required: true,
								minlength: 8,
								pwcheck: true
							},
							confirmPassword: {
								required: true,
								equalTo: "#password"
							},
							dni: {
								required: true,
								minlength: 9,
								maxlength: 9
							},
							postalCode: {
								required: true,
								minlength: 5,
								maxlength: 5
							},
							province: "required",
							city: "required",
							birthDate: "required",
							weight: {
								required: true,
								min: 1
							},
							height: {
								required: true,
								min: 1
							},
							activityLevel: "required",
							goal: "required",
							biography: "required",
							specialty: "required",
							gymName: "required",
							gymLocation: "required"
						},
						messages: {
							firstName: "Por favor, introduce tu nombre",
							lastName: "Por favor, introduce tus apellidos",
							username: "Por favor, introduce un correo electrónico válido",
							password: {
								required: "Por favor, introduce una contraseña",
								minlength: "Tu contraseña debe tener al menos 8 caracteres",
								pwcheck: "Tu contraseña debe contener al menos una letra minúscula y un número"
							},
							confirmPassword: {
								required: "Por favor, confirma tu contraseña",
								equalTo: "Las contraseñas no coinciden"
							},
							dni: {
								required: "Por favor, introduce tu DNI",
								minlength: "El DNI debe tener 9 caracteres",
								maxlength: "El DNI debe tener 9 caracteres"
							},
							postalCode: {
								required: "Por favor, introduce tu código postal",
								minlength: "El código postal debe tener 5 dígitos",
								maxlength: "El código postal debe tener 5 dígitos"
							},
							province: "Por favor, introduce tu provincia",
							city: "Por favor, introduce tu ciudad",
							birthDate: "Por favor, introduce tu fecha de nacimiento",
							weight: {
								required: "Por favor, introduce tu peso",
								min: "El peso debe ser un número positivo"
							},
							height: {
								required: "Por favor, introduce tu altura",
								min: "La altura debe ser un número positivo"
							},
							activityLevel: "Por favor, selecciona tu nivel de actividad",
							goal: "Por favor, selecciona tu objetivo de fitness",
							biography: "Por favor, introduce tu biografía",
							specialty: "Por favor, introduce tu especialidad",
							gymName: "Por favor, introduce el nombre de tu gimnasio",
							gymLocation: "Por favor, introduce la ubicación de tu gimnasio"
						}
					});
		});

$("#dni")
	.change(
		function() {
			var dni = $(this).val();
			$
				.ajax({
					url: '/api/check-dni', // URL de tu API para verificar el DNI
					type: 'POST',
					data: {
						dni: dni
					},
					success: function(data) {
						if (data.dniExists) {
							alert("El DNI ya existe. Por favor, ingresa un DNI diferente.");
							$("#dni").val('').focus();
						}
					}
				});
		});
// Rellenar el selector de países
fetch('https://restcountries.com/v3.1/all')
	.then(response => response.json())
	.then(data => {
		const countrySelect = document.getElementById('countrySelect');
		data.forEach(country => {
			const option = document.createElement('option');
			option.value = country.name.common;
			option.textContent = country.name.common;
			countrySelect.appendChild(option);
		});
	});

// Rellenar el selector de ciudades
document.getElementById('countrySelect').addEventListener('change', function() {
	const country = this.value;
	fetch(`https://countriesnow.space/api/v0.1/countries/cities`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ country: country })
	})
		.then(response => response.json())
		.then(data => {
			const citySelect = document.getElementById('citySelect');
			citySelect.innerHTML = '<option value="">Selecciona una ciudad</option>';
			if (data.data) {
				data.data.forEach(city => {
					const option = document.createElement('option');
					option.value = city;
					option.textContent = city;
					citySelect.appendChild(option);
				});
			} else {
				console.error('No se encontraron ciudades para el país seleccionado.');
			}
		})
		.catch(error => console.error('Error al obtener las ciudades:', error));
});
document.getElementById('imagenPerfilFile').addEventListener('change', function(event) {
	const file = event.target.files[0];
	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const img = new Image();
			img.src = e.target.result;
			img.onload = function() {
				// Crear un canvas y dibujar la imagen con la nueva resolución
				const canvas = document.createElement('canvas');
				const maxWidth = 300; // Ancho máximo
				const maxHeight = 300; // Alto máximo
				let width = img.width;
				let height = img.height;

				// Mantener la relación de aspecto
				if (width > height) {
					if (width > maxWidth) {
						height = Math.round((maxWidth / width) * height);
						width = maxWidth;
					}
				} else {
					if (height > maxHeight) {
						width = Math.round((maxHeight / height) * width);
						height = maxHeight;
					}
				}

				canvas.width = width;
				canvas.height = height;

				// Dibujar la imagen redimensionada
				const ctx = canvas.getContext('2d');
				ctx.drawImage(img, 0, 0, width, height);

				// Obtener la imagen comprimida en base64
				const compressedBase64 = canvas.toDataURL('image/jpeg', 0.7); // Calidad al 70%
				// Almacenar la imagen en el campo oculto (sin el encabezado base64)
				document.getElementById('imagenPerfilBase64').value = compressedBase64.split(',')[1];
			};
		};
		reader.readAsDataURL(file);
	}
});
document
	.getElementById('cookImages')
	.addEventListener('change', function(event) {
		const files = event.target.files; // Get the list of files
		const base64Images = []; // Array to store Base64 strings

		Array.from(files).forEach(file => {
			if (file) {
				const reader = new FileReader();
				reader.onload = function(e) {
					const img = new Image();
					img.src = e.target.result;
					img.onload = function() {
						// Create a canvas and draw the image with the new resolution
						const canvas = document.createElement('canvas');
						const maxWidth = 300; // Maximum width
						const maxHeight = 300; // Maximum height
						let width = img.width;
						let height = img.height;

						// Maintain the aspect ratio
						if (width > height) {
							if (width > maxWidth) {
								height = Math.round((maxWidth / width) * height);
								width = maxWidth;
							}
						} else {
							if (height > maxHeight) {
								width = Math.round((maxHeight / height) * width);
								height = maxHeight;
							}
						}

						canvas.width = width;
						canvas.height = height;

						// Draw the resized image
						const ctx = canvas.getContext('2d');
						ctx.drawImage(img, 0, 0, width, height);

						// Get the compressed image in base64
						const compressedBase64 = canvas.toDataURL('image/jpeg', 0.7); // Quality at 70%

						// Store the image in the base64Images array (without the base64 header)
						base64Images.push(compressedBase64.split(',')[1]);

						// Update a hidden input with all Base64 images, if desired
						document.getElementById('cookImagesBase64').value = base64Images.join(',');
					};
				};
				reader.readAsDataURL(file);
			}
		});
	});
// Obtener la fecha actual y calcular las fechas mínimas y máximas permitidas
const today = new Date();
const minAge = 18;
const maxAge = 80;

const minDate = new Date(today.getFullYear() - maxAge, today.getMonth(), today.getDate());
const maxDate = new Date(today.getFullYear() - minAge, today.getMonth(), today.getDate());

// Establecer el rango permitido para la fecha de nacimiento
document.getElementById("birthDate").setAttribute("min", minDate.toISOString().split('T')[0]);

