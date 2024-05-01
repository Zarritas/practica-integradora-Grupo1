const pregunta = document.getElementById("pregunta");
const respuesta = document.getElementById("respuesta");
const boton = document.getElementById("boton");

function mostrarPregunta() {
    let preg = document.createElement("input");
    preg.setAttribute("type", "text");
    preg.setAttribute("value", "pregunta");
    pregunta.appendChild("preg");
}