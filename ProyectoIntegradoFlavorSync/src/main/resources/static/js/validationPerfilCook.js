// Ejemplo de JavaScript para deshabilitar el envío de formularios si hay campos no válidos
(function() {
	'use strict'

	// Obtener todos los formularios a los que queremos aplicar estilos de validación de Bootstrap personalizados
	var forms = document.querySelectorAll('.needs-validation')

	// Bucle sobre ellos y evitar el envío
	Array.prototype.slice.call(forms)
		.forEach(function(form) {
			form.addEventListener('submit', function(event) {
				if (!form.checkValidity()) {
					event.preventDefault()
					event.stopPropagation()
				}

				form.classList.add('was-validated')
			}, false)
		})
})()
function togglePassword() {
	const passwordField = document.getElementById('password');
	const togglePasswordIcon = document.getElementById('togglePasswordIcon');
	const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
	passwordField.setAttribute('type', type);
	togglePasswordIcon.classList.toggle('fa-eye-slash');
}
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