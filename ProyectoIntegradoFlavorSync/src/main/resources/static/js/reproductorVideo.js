// Abrir el modal
document.getElementById("openModalBtn").addEventListener("click", openModal);

function openModal() {
    document.getElementById("videoModal").style.display = "block";
}

// Cerrar el modal
function closeModal() {
    document.getElementById("videoModal").style.display = "none";
}

// Arrastrar el modal
let modal = document.getElementById("videoModal");
let modalContent = document.querySelector(".modal-content");

modalContent.addEventListener("mousedown", (e) => {
    const offsetX = e.clientX - modal.offsetLeft;
    const offsetY = e.clientY - modal.offsetTop;

    function dragModal(e) {
        modal.style.left = `${e.clientX - offsetX}px`;
        modal.style.top = `${e.clientY - offsetY}px`;
    }

    function stopDrag() {
        document.removeEventListener("mousemove", dragModal);
        document.removeEventListener("mouseup", stopDrag);
    }

    document.addEventListener("mousemove", dragModal);
    document.addEventListener("mouseup", stopDrag);
});

// Redimensionar el modal
let resizeHandle = document.querySelector(".resize-handle");

resizeHandle.addEventListener("mousedown", (e) => {
    const initialWidth = modal.offsetWidth;
    const initialHeight = modal.offsetHeight;
    const initialX = e.clientX;
    const initialY = e.clientY;

    function resizeModal(e) {
        const width = initialWidth + (e.clientX - initialX);
        const height = initialHeight + (e.clientY - initialY);

        modal.style.width = `${width}px`;
        modal.style.height = `${height}px`;
    }

    function stopResize() {
        document.removeEventListener("mousemove", resizeModal);
        document.removeEventListener("mouseup", stopResize);
    }

    document.addEventListener("mousemove", resizeModal);
    document.addEventListener("mouseup", stopResize);
});
