// Ejemplo de JavaScript para deshabilitar el envío de formularios si hay campos no válidos
(function () {
    'use strict'

    // Obtener todos los formularios a los que queremos aplicar estilos de validación de Bootstrap personalizados
    var forms = document.querySelectorAll('.needs-validation')

    // Bucle sobre ellos y evitar el envío
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
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