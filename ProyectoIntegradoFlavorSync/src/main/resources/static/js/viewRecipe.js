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
document.getElementById('downloadPdf').addEventListener('click', function () {
       const elementToPrint = document.querySelector('.container'); // Ajusta este selector según el contenedor que quieras exportar.

       const options = {
           background: '#fff',
           scale: 2, // Aumenta la calidad de la captura.
       };

       html2canvas(elementToPrint, options).then((canvas) => {
           const imgData = canvas.toDataURL('image/png'); // Convertir el canvas a imagen PNG.
           const pdf = new jsPDF({
               orientation: 'portrait',
               unit: 'px',
               format: [canvas.width, canvas.height], // Formato basado en la altura del canvas.
           });

           pdf.addImage(imgData, 'PNG', 0, 0, canvas.width, canvas.height);
           pdf.save('receta.pdf'); // Nombre del archivo de salida.
       });
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
