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
$('#role').change(function() {
	var role = $(this).val();
	$('#gymUserFields').toggle(role === 'GymUser');
	$('#instructorFields').toggle(role === 'Instructor');
	$('#gymOwnerFields').toggle(role === 'GymOwner');
}).change();
// Show the fields for the selected role on page load
$('#role').change();
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
