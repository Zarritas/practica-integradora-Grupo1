const parte1 = document.getElementById("email_boton");
const email = document.getElementById("email");
const mensajeError1 = document.getElementById("mensaje_error1");
const parte2 = document.getElementById("pregunta_respuesta");
parte2.style.display = "none";
const question = document.getElementById("question");
const answer = document.getElementById("answer");
const mensajeError2 = document.getElementById("mensaje_error2");
const parte3 = document.getElementById("clave_volver");
parte3.style.display = "none";
const claveRecuperada = document.getElementById("clave_recuperada");
let preg;
let resp;
let contasena;

function mostrarPregunta() {
    $.get(`http://localhost:8080/user/recuperacion/${email.value}`, function(data){
        if (data === '') {
            mensajeError1.innerHTML = "Email incorrecto";
        } else {
            parte1.style.display = "none";
            parte2.style.display = "block";
            preg = data.recuperacionClave.pregunta.pregunta;
            question.innerHTML = preg;
            resp = data.recuperacionClave.respuesta;
            contasena = data.clave;
        }
    });
}

function comprobarRespuesta() {
    if (answer.value === resp) {
        parte2.style.display = "none";
        parte3.style.display = "block";
        claveRecuperada.innerHTML = "La contraseña es: " + contasena;
    } else {
        mensajeError2.innerHTML = "Respuesta incorrecta";
    }
}