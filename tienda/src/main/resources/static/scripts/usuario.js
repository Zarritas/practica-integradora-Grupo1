const ojoClave = document.getElementById("btn_mostrar_contrasena");
const clave = document.getElementById("clave");
const ojoClaveRepetida = document.getElementById("btn_mostrar_contrasena_confirma");
const confirmaClave = document.getElementById("confirmaClave");

const usuario = document.getElementById("usuario");



function asignarEventos() {
    if (ojoClave != null && ojoClaveRepetida != null) {
        ojoClave.addEventListener('click', mostrarClave);
        ojoClaveRepetida.addEventListener('click', mostrarClave);
    }
}

function mostrarClave(elemento) {
    let objeto;
    if (elemento.target === ojoClave) {
        objeto = clave;
    }
    if (elemento.target === ojoClaveRepetida) {
        objeto = confirmaClave;
    }
    if (objeto.type === "password") {
        objeto.type = "text";
    } else {
        objeto.type = "password";
    }
    elemento.target.classList.toggle("bi-eye");
}
asignarEventos();

function desconectar() {
    window.location.assign("http://localhost:8080/usuario/autadmin");
}