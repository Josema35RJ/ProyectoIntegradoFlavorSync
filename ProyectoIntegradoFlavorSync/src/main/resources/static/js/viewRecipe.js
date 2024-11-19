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
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('downloadPdf').addEventListener('click', async function () {
        // Asegúrate de importar `jsPDF` del espacio de nombres correcto
        const { jsPDF } = window.jspdf;

        // Selecciona el contenedor principal para capturar
        const elementToPrint = document.querySelector('.container');

        // Opciones para html2canvas
        const options = {
            background: '#fff',
            scale: 2, // Mejora la calidad de la captura
        };

        try {
            const canvas = await html2canvas(elementToPrint, options); // Renderiza el HTML en un canvas
            const imgData = canvas.toDataURL('image/png'); // Convierte el canvas en imagen
            const pdf = new jsPDF({
                orientation: 'portrait',
                unit: 'px',
                format: [canvas.width, canvas.height], // Ajusta el tamaño al canvas
            });

            pdf.addImage(imgData, 'PNG', 0, 0, canvas.width, canvas.height); // Añade la imagen al PDF
            pdf.save('detalle_receta.pdf'); // Guarda el archivo
        } catch (error) {
            console.error('Error generando el PDF:', error); // Manejo de errores
        }
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
});
