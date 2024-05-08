<script>
// import axios from 'axios';

import axios from "axios";

export default {
  data() {
    return {
      editando: true,
      tiposDeMongo: ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean'] // Tipos de datos en MongoDB
    };
  },
  methods:{
    guardarProductos() {
      const formData = new FormData(document.getElementById('formulario'));
      axios.post('http://localhost:8080/producto/crear', formData)
          .then(response => {
            if (response.data.success) {
              alert(response.data.message);
              console.log(response.data);
              this.editando = false;
              window.location.href = "http://172.19.0.18:8080";
              // window.location.href = "http://productos.poketienda.com/";
            } else {
              console.error("Error al realizar la solicitud:", response);
              alert("Error: " + response.data.mensaje); // Cambié response.data.message por response.data.mensaje

              // Aplicar estilos de Bootstrap a los campos con errores
              const camposConErrores = response.data.camposConErrores;

              // Limpiar errores anteriores antes de agregar nuevos
              try {
                let errores = document.getElementsByClassName("is-invalid")
                for (let errorclase of errores) {
                  errorclase.remove()
                }
              }catch {
                console.log("holi")
              }
              // Agregar nuevos errores
              Object.entries(camposConErrores).forEach(([key, value]) => {
                const elemento = document.getElementById('atr-'+key);
                if (elemento) {
                  let div = document.createElement('div')
                  div.id = "error-"+key
                  div.classList.add('is-invalid');
                  div.appendChild(document.createTextNode(value))
                  elemento.parentElement.appendChild(div);
                }
              });
            }
          })
          .catch(error => {
            console.error("Error al realizar la solicitud:", error.response);
            alert("Error: " + error.response.data.mensaje); // Cambié response.data.message por response.data.mensaje

            // Aplicar estilos de Bootstrap a los campos con errores
            const camposConErrores = error.response.data.camposConErrores;

            // Limpiar errores anteriores antes de agregar nuevos
            try {
              let errores = document.getElementsByClassName("is-invalid")
              for (let error of errores) {
                error.remove()
              }
            }catch {
              console.log("holi")
            }
            // Agregar nuevos errores
            Object.entries(camposConErrores).forEach(([key, value]) => {
              const elemento = document.getElementById('atr-'+key);
              if (elemento) {
                let div = document.createElement('div')
                div.id = "error-"+key
                div.classList.add('is-invalid');
                div.appendChild(document.createTextNode(value))
                elemento.parentElement.appendChild(div);
              }
            });
          });
    },
    nuevoAtributo(){
      let tiposDeMongo = ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean']
      let formulario = document.getElementById("cuerpo-form")
      let div = document.createElement("div")
      div.id="nuevo_atributo"

      let label = document.createElement("label")
      label.id="nuevo_atributo"
      label.innerText = "Nuevo Atributo:"

      let input_nombre = document.createElement("input")
      input_nombre.type = "text"

      let input_value = document.createElement("input")
      input_value.type = "text"

      let select = document.createElement("select")
      select.classList = 'form-select'

      for(let tipo of tiposDeMongo){
        let opcion = document.createElement("option")
        opcion.innerText=tipo
        opcion.value=tipo
        if (tipo === 'String')
          opcion.selected
        select.appendChild(opcion)
      }
      div.append(label,input_nombre,input_value,select)
      formulario.append(div)
      input_nombre.addEventListener("blur",function (){
        let nuevo_atributo = document.getElementById("nuevo_atributo")
        let name = nuevo_atributo.children[2].value
        nuevo_atributo.children[0].removeAttribute("id")
        nuevo_atributo.children[0].setAttribute("for",name)
        nuevo_atributo.children[0].innerText = name+': '
        nuevo_atributo.children[1].setAttribute("name","_"+name)
        nuevo_atributo.children[1].setAttribute("value",name)
        nuevo_atributo.children[2].setAttribute("name",name)
        nuevo_atributo.children[3].setAttribute("name","tipo-"+name)
      })
    }
  }
};
</script>

<template>
<div>
  <h1>Nuevo Producto<span v-if="editando">*</span></h1>
  <form enctype="multipart/form-data" id="formulario">
    <div class="modal-body" id="cuerpo-form">
      <div class="row">
        <div id="nombre">
          <div class="col-md-4">
            <label for="atr-nombre">Nombre:</label>
          </div>
          <div class="col-md-4">
            <input type="text" hidden="hidden" name="_nombre" value="nombre" />
            <input type="text" name="nombre" id="atr-nombre" placeholder="Ingresar nombre de producto" />
          </div>
          <div class="col-md-4">
            <select class="form-select" name="tipo-nombre">
              <option value="String" selected>Texto</option>
            </select>
          </div>
        </div>
      </div>
      <div>
        <div class="col-md-4">
          <label for="atr-imagenes">Imagen de Perfil:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_imagen_perfil" value="imagen_perfil" />
          <input type="file" name="imagen_perfil" id="atr-imagen_perfil" />
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-imagen_perfil">
            <option value="Binary" selected>Imagen</option>
          </select>
        </div>
      </div>
      <div class="row">
        <div id="fecha_creacion">
          <div class="col-md-4">
            <label for="atr-fecha_creacion">Fecha de creación:</label>
          </div>
          <div class="col-md-4">
            <input type="text" hidden="hidden" name="_fecha_creacion" value="fecha_creacion" />
            <input type="date" name="fecha_creacion" id="atr-fecha_creacion"/>
          </div>
          <div class="col-md-4">
            <select class="form-select" name="tipo-fecha_creacion">
              <option value="Date" selected>Fecha</option>
            </select>
          </div>
        </div>
      </div>
      <div id="fecha_ultima_modificacion">
        <div class="col-md-4">
          <label for="atr-fecha_ultima_modificacion">Fecha de última modificación:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_fecha_ultima_modificacion" value="fecha_ultima_modificacion"/>
          <input type="date" name="fecha_ultima_modificacion" id="atr-fecha_ultima_modificacion"/>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-fecha_ultima_modificacion">
            <option value="Date" selected>Fecha</option>
          </select>
        </div>
      </div>
      <div id="precio">
        <div class="col-md-4">
          <label for="atr-precio">Precio:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_precio" value="precio"/>
          <input type="text" name="precio" id="atr-precio" placeholder="Ingresar precio de producto"/>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-precio">
            <option value="Number" selected>Número</option>
          </select>
        </div>
      </div>
      <div id="descripcion">
        <div class="col-md-4">
          <label for="atr-descripcion">Descripción:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_descripcion" value="descripcion"/>
          <textarea name="descripcion" id="atr-descripcion" placeholder="Ingresar descripción del producto"></textarea>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-descripcion">
            <option value="String" selected>Texto</option>
          </select>
        </div>
      </div>
      <div id="categoria">
        <div class="col-md-4">
          <label for="atr-categoria">Categoría:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_categoria" value="categoria"/>
          <input type="text" name="categoria" id="atr-categoria" placeholder="Ingresar categoría de producto"/>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-categoria">
            <option value="String" selected>Texto</option>
          </select>
        </div>
      </div>
      <div id="almacen">
        <div class="col-md-4">
          <label for="atr-en_almacen">Unidades en almacén:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_en_almacen" value="en_almacen"/>
          <input type="number" name="en_almacen" id="atr-en_almacen"/>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-en_almacen">
            <option value="Number" selected>Número</option>
          </select>
        </div>
      </div>
      <div id="tipo">
        <div class="col-md-4">
          <label for="atr-tipo">Tipo:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_tipo" value="tipo"/>
          <input type="text" name="tipo" id="atr-tipo" placeholder="Ingresar tipo de producto"/>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-tipo">
            <option value="String" selected>Texto</option>
          </select>
        </div>
      </div>
      <div id="imagenes">
        <div class="col-md-4">
          <label for="atr-imagenes">Imagenes:</label>
        </div>
        <div class="col-md-4">
          <input type="text" hidden="hidden" name="_imagenes" value="imagenes"/>
          <input type="file" name="imagenes" id="atr-imagenes" multiple/>
        </div>
        <div class="col-md-4">
          <select class="form-select" name="tipo-imagenes">
            <option value="Binary" selected>Imagenes</option>
          </select>
        </div>
      </div>
    </div>
    <div id="botones-form" class="modal-footer">
      <div @click="guardarProductos()" class="btn btn-success">Guardar producto</div>
      <button type="reset" class="btn btn-secondary">Limpiar todo</button>
      <div @click="nuevoAtributo()" class="btn btn-primary">Nuevo Atributo</div>
    </div>
  </form>
</div>
</template>

<style>
.is-invalid{
  color: red;
}
</style>