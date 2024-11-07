var commentModal = document.getElementById('commentModal');
commentModal.addEventListener('show.bs.modal', function() {
	resetStarRating();
});

commentModal.addEventListener('click', function(event) {
	if (event.target.classList.contains('star')) {
		var ratingValue = event.target.getAttribute('data-value');
		setStarRating(ratingValue);
	}
});

function setStarRating(rating) {
	document.getElementById('rating').value = rating;
	var stars = commentModal.querySelectorAll('.star');
	stars.forEach(function(star) {
		star.classList.remove('selected');
	});
	for (let i = 0; i < rating; i++) {
		stars[i].classList.add('selected');
	}
}

function resetStarRating() {
	document.getElementById('rating').value = 0;
	var stars = commentModal.querySelectorAll('.star');
	stars.forEach(function(star) {
		star.classList.remove('selected');
	});
}
document.getElementById("downloadPdf").addEventListener("click", function() {
	const { jsPDF } = window.jspdf;
	const doc = new jsPDF();

	// Función para agregar una sección con un título
	function addSectionTitle(title, y) {
		doc.setFontSize(16);
		doc.setTextColor(0, 102, 204); // Color azul para el título
		doc.setFont('Helvetica', 'bold');
		doc.text(title, 20, y);
	}

	// Función para agregar una lista de elementos
	function addList(items, startY) {
		doc.setFontSize(12);
		doc.setTextColor(0, 0, 0); // Color negro para el texto
		items.forEach((item, index) => {
			doc.text(`• ${item}`, 20, startY + (index * 10));
		});
	}

	// Función para agregar una caja con texto
	function addBoxedText(text, y) {
		doc.setFontSize(12);
		doc.setTextColor(0, 0, 0);
		const pageWidth = doc.internal.pageSize.width;
		const margin = 20;
		const textWidth = doc.getTextWidth(text);
		const boxWidth = Math.min(textWidth + 10, pageWidth - 2 * margin); // Ajuste el tamaño de la caja
		doc.setDrawColor(200, 200, 200);
		doc.setFillColor(240, 240, 240); // Color de fondo gris claro
		doc.rect(margin, y - 3, boxWidth, 10, 'F'); // Caja con fondo
		doc.text(text, margin + 5, y);
	}

	// Título principal
	doc.setFontSize(22);
	doc.setTextColor(0, 102, 204); // Azul para el título
	doc.setFont('Helvetica', 'bold');
	doc.text("Detalle de la Receta", 20, 20);
	doc.setDrawColor(0, 102, 204);
	doc.setLineWidth(1);
	doc.line(20, 25, 190, 25);

	// Información de la receta
	const recipeName = document.querySelector('.recipe-header h1')?.innerText || "Nombre de la Receta No Disponible";
	const preparationTime = document.querySelector('ul li:nth-child(2)')?.innerText.split(": ")[1] || "No disponible";
	const diners = document.querySelector('ul li:nth-child(1)')?.innerText.split(": ")[1] || "No disponible";
	const difficulty = document.querySelector('.badge.bg-danger')?.innerText || "No disponible";

	doc.setFontSize(16);
	doc.setTextColor(0, 0, 0); // Texto negro
	doc.text(`Nombre de la Receta: ${recipeName}`, 20, 35);
	doc.text(`Tiempo de Preparación: ${preparationTime}`, 20, 45);
	doc.text(`Comensales: ${diners}`, 20, 55);
	doc.text(`Dificultad: ${difficulty}`, 20, 65);

	// Ingredientes
	const ingredients = Array.from(document.querySelectorAll('.recipe-section ul.list-group li')).map(li => li.innerText);
	addSectionTitle("Ingredientes:", 80);
	addList(ingredients, 90);

	// Instrucciones
	const instructions = document.querySelector('.bg-light')?.innerText || "Instrucciones no disponibles.";
	const instructionsY = 90 + (ingredients.length * 10) + 10;
	addSectionTitle("Instrucciones:", instructionsY);
	addBoxedText(instructions, instructionsY + 10);

	// Galería de Imágenes de la receta
	const recipeImages = Array.from(document.querySelectorAll('.image-gallery img')).map(img => img.src);
	if (recipeImages.length) {
		let imageY = instructionsY + 30;
		recipeImages.forEach((image, index) => {
			if (index % 2 === 0 && index > 0) {
				doc.addPage(); // Nueva página para más imágenes
				imageY = 10;
			}
			doc.addImage(image, 'JPEG', (index % 2 === 0 ? 10 : 110), imageY, 90, 60);
			if (index % 2 !== 0) imageY += 70;
		});
	} else {
		doc.text("No hay imágenes de la receta disponibles.", 20, instructionsY + 30);
	}

	// Establecer pie de página
	doc.setFontSize(8);
	doc.setTextColor(150);
	doc.text("Generado con love by RecipeApp", 20, doc.internal.pageSize.height - 10);

	// Guardar el PDF
	doc.save('receta.pdf');
});
document.addEventListener('DOMContentLoaded', function() {
	// Función para mostrar el toast
	function showToast(message, type) {
		const toastContainer = document.getElementById('toast-container');
		const toast = document.createElement('div');
		toast.classList.add('toast');
		if (type === 'success') {
			toast.classList.add('success');
			toast.textContent = message;
		} else {
			toast.classList.add('error');
			toast.textContent = message;
		}
		toastContainer.appendChild(toast);

		// Mostrar el toast
		setTimeout(() => {
			toast.classList.add('show');
		}, 100);

		// Ocultar el toast después de 3 segundos
		setTimeout(() => {
			toast.classList.remove('show');
			setTimeout(() => {
				toastContainer.removeChild(toast);
			}, 500);
		}, 3000);
	}
	document.querySelectorAll('.favorite-btn').forEach(button => {
		button.addEventListener('click', function() {
			const recipeId = this.getAttribute('data-recipe-id'); // Obtener el ID de la receta
			const icon = this.querySelector('i'); // Obtener el ícono dentro del botón

			// Hacer una solicitud POST para cambiar el estado de favoritos en la base de datos
			fetch(`/auth/cook/favoriteRecipe/${recipeId}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			})
				.then(response => {
					if (response.ok) {
						// Alternar la clase del ícono dependiendo del estado de `booleanFav`
						if (icon.classList.contains('far')) {
							// Si el corazón está vacío (no favorito), ahora lo añadimos (favorito)
							icon.classList.remove('far');
							icon.classList.add('fas');
							showToast('Receta añadida a favoritos', 'success');
						} else {
							// Si el corazón está lleno (favorito), lo eliminamos (no favorito)
							icon.classList.remove('fas');
							icon.classList.add('far');
							showToast('Receta eliminada de favoritos', 'success');
						}
					} else {
						// Si no funciona, mostrar error
						showToast('Ocurrió un error al añadir a favoritos.', 'error');
					}
				})
				.catch(error => {
					console.error('Error al procesar la solicitud:', error);
					showToast('Error al procesar la solicitud.', 'error');
				});
		});
	});
	document.querySelectorAll('.favorite-btn').forEach(button => {
		const icon = button.querySelector('i'); // Obtener el ícono dentro del botón
		const booleanFav = button.getAttribute('data-favorite') === 'true'; // Si el atributo 'data-favorite' es 'true', está añadido a favoritos

		if (booleanFav) {
			// Si la receta está en favoritos, mostramos el corazón lleno
			icon.classList.remove('far');
			icon.classList.add('fas');
		} else {
			// Si no está en favoritos, mostramos el corazón vacío
			icon.classList.remove('fas');
			icon.classList.add('far');
		}
	});
});
