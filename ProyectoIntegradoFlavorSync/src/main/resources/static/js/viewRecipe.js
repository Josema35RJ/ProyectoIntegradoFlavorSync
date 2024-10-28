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
		    
		    // Título del PDF
		    doc.setFontSize(22);
		    doc.setTextColor(40);
		    doc.text("Detalle de la Receta", 20, 20);
		    
		    // Línea separadora
		    doc.setDrawColor(40);
		    doc.setLineWidth(1);
		    doc.line(20, 25, 190, 25);

		    // Información de la receta
		    const recipeName = document.querySelector('.recipe-header h1')?.innerText || "Nombre de la Receta No Disponible";
		    doc.setFontSize(16);
		    doc.text(`Nombre de la Receta: ${recipeName}`, 20, 35);

		    // Obtener información general
		    const infoItems = document.querySelectorAll('ul li');
		    const preparationTime = infoItems.length > 1 ? infoItems[1].innerText.split(": ")[1]?.trim() : "No disponible";
		    const diners = infoItems.length > 0 ? infoItems[0].innerText.split(": ")[1]?.trim() : "No disponible";
		    const difficultyBadge = document.querySelector('.badge.bg-danger');
		    const difficulty = difficultyBadge ? difficultyBadge.innerText : "No disponible";

		    doc.text(`Tiempo de Preparación: ${preparationTime}`, 20, 45);
		    doc.text(`Comensales: ${diners}`, 20, 55);
		    doc.text(`Dificultad: ${difficulty}`, 20, 65);

		    // Añadir imagen de perfil (si aplica)
		    const profileImage = document.getElementById("profileImage")?.src;
		    if (profileImage) {
		        doc.addImage(profileImage, 'JPEG', 10, 70, 50, 50); // Ajustar posición y tamaño
		    }

		    // Ingredientes
		    doc.setFontSize(14);
		    doc.setFillColor(255, 220, 220); // Color de fondo
		    doc.rect(10, 120, 190, 30, 'F'); // Fondo de sección
		    doc.text("Ingredientes:", 20, 135);
		    
		    const ingredients = Array.from(document.querySelectorAll('.list-group-item')).map(li => li.innerText);
		    ingredients.forEach((ingredient, index) => {
		        doc.text(`• ${ingredient}`, 20, 145 + (index * 10));
		    });

		    // Instrucciones
		    doc.setFontSize(14);
		    doc.setFillColor(220, 255, 220); // Color de fondo
		    doc.rect(10, 145 + (ingredients.length * 10), 190, 30, 'F'); // Fondo de sección
		    doc.text("Instrucciones:", 20, 145 + (ingredients.length * 10 + 10));
		    const instructions = document.querySelector('.bg-light')?.innerText || "Instrucciones no disponibles.";
		    doc.setFontSize(12);
		    doc.text(instructions, 20, 145 + (ingredients.length * 10 + 20));

		    // Añadir imágenes de la receta
		    const recipeImages = Array.from(document.querySelectorAll('.recipe-image')).map(img => img.src);
		    let imageY = 10;
		    recipeImages.forEach((image, index) => {
		        if (index % 2 === 0 && index > 0) {
		            doc.addPage(); // Nueva página para imágenes
		            imageY = 10;
		        }
		        doc.addImage(image, 'JPEG', (index % 2 === 0 ? 10 : 110), imageY, 90, 60); // Dos imágenes por fila
		        if (index % 2 === 0) {
		            imageY += 70; // Espacio para la siguiente imagen
		        }
		    });

		    // Guardar el PDF
		    doc.save('receta.pdf');
		});
		document.addEventListener('DOMContentLoaded', function () {
		    // Añadir eventos a los botones de favoritos
		    document.querySelectorAll('.favorite-btn').forEach(button => {
		        button.addEventListener('click', function () {
		            const recipeId = this.getAttribute('data-recipe-id'); // Obtener el ID de la receta
		            const icon = this.querySelector('i'); // Obtener el ícono dentro del botón

		            fetch(`/auth/cook/favoriteRecipe/${recipeId}`, {
		                method: 'POST',
		                headers: {
		                    'Content-Type': 'application/x-www-form-urlencoded'
		                }
		            })
		            .then(response => {
		                if (response.ok) {
		                    // Cambiar el ícono entre vacío y lleno
		                    icon.classList.toggle('fas'); // Corazón lleno (favorito)
		                    icon.classList.toggle('far'); // Corazón vacío (no favorito)
		                    // Puedes agregar una alerta o notificación visual si lo deseas
		                    alert('¡Receta añadida a favoritos exitosamente!'); 
		                } else {
		                    // Si no funciona, notificar error
		                    alert('Ocurrió un error al añadir a favoritos.');
		                }
		            })
		            .catch(error => {
		                console.error('Error al procesar la solicitud:', error);
		                alert('Error al procesar la solicitud.');
		            });
		        });
		    });
		});