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
/*$(document).ready(function() {
	// Inicializar select2
	$('#ingredientsIntegers').select2({
		placeholder: 'Selecciona los ingredientes',
		allowClear: true
	});

	// Obtener el select y el contenedor donde agregaremos las entradas de cantidad y unidad
	const ingredientSelect = document.getElementById('ingredientsIntegers');
	const container = document.getElementById('ingredientDetailsContainer');

	// Escuchar cambios en el select de ingredientes
	ingredientSelect.addEventListener('change', function() {
		// Limpiar el contenedor
		container.innerHTML = '';

		// Para cada ingrediente seleccionado, añadir campos de cantidad y unidad
		Array.from(ingredientSelect.selectedOptions).forEach((option) => {
			const ingredientId = option.value;
			const ingredientName = option.textContent;

			// Crear un contenedor para este ingrediente
			const ingredientDiv = document.createElement('div');
			ingredientDiv.classList.add('ingredient-input');
			ingredientDiv.innerHTML = `
                       <label>${ingredientName}:</label>
                       <input type="hidden" name="ingredientsIds[]" value="${ingredientId}">
                       <input type="number" name="ingredientsCant[]" placeholder="Cantidad" required>
                       <input type="text" name="ingredientsUnit[]" placeholder="Unidad" required>
                   `;
			container.appendChild(ingredientDiv);
		});
	});
});*/
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
                document.getElementById('imagenRecipeBase64').value = compressedBase64.split(',')[1];
            };
        };
        reader.readAsDataURL(file);
    }
});