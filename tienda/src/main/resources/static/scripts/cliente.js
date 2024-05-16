const tarjeta= document.getElementById("tarjeta1");
let tarjetas= document.getElementById("tarjetas");
const btn= document.getElementById("nuevaTarjeta");
let contador = 1;

function crearNuevaTarjeta(){
    let nuevaTarjeta= tarjeta.cloneNode(true);
    let tituloNuevaTarjeta= nuevaTarjeta.childNodes[1];
    let id_tarjeta = tarjeta.id
    id_tarjeta = parseInt(id_tarjeta.charAt(id_tarjeta.length - 1)) + contador
    nuevaTarjeta.id = "tarjeta" + id_tarjeta
    tituloNuevaTarjeta.id = "Tarjeta " + id_tarjeta
    tituloNuevaTarjeta.innerHTML = tituloNuevaTarjeta.id;
    tarjetas.appendChild(nuevaTarjeta)
    contador++
}
btn.addEventListener("click", crearNuevaTarjeta);
