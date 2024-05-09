<script>
// import axios from 'axios';

import axios from "axios";

export default {
  data() {
    return {
      editando: true,
      tiposDeMongo: ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean'],
      atributos: [], // Array para almacenar los atributos
      nuevoAtributo: { nombre: '', tipo: '', valor: '', guardado: false }, // Nuevo atributo
      nuevoAtributoVisible: false // Controla la visibilidad del formulario para nuevo atributo
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
              alert("Error: " + response.data.mensaje);

              // Aplicar estilos de Bootstrap a los campos con errores
              // Limpiar errores anteriores
              this.limpiarErrores();

              // Mostrar nuevos errores
              const camposConErrores = response.data.camposConErrores;
              this.mostrarErrores(camposConErrores);
            }
          })
          .catch(error => {
            console.error("Error al realizar la solicitud:", error.response);
            alert("Error: " + error.response.data.mensaje);

            // Aplicar estilos de Bootstrap a los campos con errores
            // Limpiar errores anteriores antes de agregar nuevos
            this.limpiarErrores();
            // Agregar nuevos errores
            const camposConErrores = error.response.data.camposConErrores;
            this.mostrarErrores(camposConErrores);
          });
    },
    getPlaceholder(tipo) {
      switch (tipo) {
        case 'String':
          return 'Ingrese texto';
        case 'Number':
          return 'Ingrese número';
        case 'Array':
          return 'cosa1,cosa2,cosa3,...'
        case 'Object':
          return 'parametro1:valor1,parametro2:valor2,...'
        default:
          return '';
      }
    },
    guardarAtributo(index) {
      this.atributos[index].guardado = true;
    },
    limpiarFormulario(){
      this.atributos = [];
    },
    mostrarNuevoAtributo() {
      this.nuevoAtributoVisible = true;
    },
    guardarNuevoAtributo() {
      this.atributos.push({ nombre: this.nuevoAtributo.nombre, tipo: 'String', valor: '', guardado: false });
      this.nuevoAtributoVisible = false;
      this.nuevoAtributo = { nombre: '', tipo: '', valor: '', guardado: false };
    },
    editarAtributo(index) {
      // Establecer el atributo en modo de edición
      this.atributos[index].editando = true;
    },
    eliminarAtributo(index) {
      // Eliminar el atributo del array usando splice
      this.atributos.splice(index, 1);
    },
    limpiarErrores() {
      const errores = document.querySelectorAll('.error-message');
      errores.forEach(error => {
        error.parentElement.removeChild(error);
      });
    },
    mostrarErrores(camposConErrores) {
      Object.entries(camposConErrores).forEach(([key, value]) => {
        const elemento = document.getElementById('atr-' + key);
        if (elemento) {
          let divError = elemento.parentElement.querySelector('.error-message');
          if (!divError) {
            divError = document.createElement('div');
            divError.classList.add('error-message', 'is-invalid');
            elemento.parentElement.appendChild(divError);
          }
          divError.innerText = value;
        }
      });
    },
  }
};
</script>

<template>
<div>
  <h1>Nuevo Producto<span v-if="editando">*</span></h1>
  <form enctype="multipart/form-data" id="formulario">
    <div class="modal-body" id="cuerpo-form">
      <div id="nombre">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-nombre"><strong>*</strong>Nombre:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_nombre" value="nombre" />
            <input type="text" name="nombre" id="atr-nombre" placeholder="Ingresar nombre de producto" />
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-nombre">
              <option value="String" selected>Texto</option>
            </select>
          </div>
        </div>
      </div>
      <div id="imagen_perfil">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
          <label for="atr-imagenes"><strong>*</strong>Imagen de Perfil:</label>
        </div>
          <div class="col-md-4 align-items-center text-center ">
          <input type="text" hidden="hidden" name="_imagen_perfil" value="imagen_perfil" />
          <input type="file" name="imagen_perfil" id="atr-imagen_perfil" />
        </div>
          <div class="col-md-4 align-items-center text-center ">
          <select class="form-select" name="tipo-imagen_perfil">
            <option value="Binary" selected>Imagen</option>
          </select>
        </div>
        </div>
      </div>
      <div id="fecha_creacion">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-fecha_creacion"><strong>*</strong>Fecha de creación:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_fecha_creacion" value="fecha_creacion" />
            <input type="date" name="fecha_creacion" id="atr-fecha_creacion"/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-fecha_creacion">
              <option value="Date" selected>Fecha</option>
            </select>
          </div>
        </div>
      </div>
      <div id="fecha_ultima_modificacion">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-fecha_ultima_modificacion"><strong>*</strong>Fecha de última modificación:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_fecha_ultima_modificacion" value="fecha_ultima_modificacion"/>
            <input type="date" name="fecha_ultima_modificacion" id="atr-fecha_ultima_modificacion"/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-fecha_ultima_modificacion">
              <option value="Date" selected>Fecha</option>
            </select>
          </div>
        </div>
      </div>
      <div id="precio">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-precio"><strong>*</strong>Precio:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_precio" value="precio"/>
            <input type="text" name="precio" id="atr-precio" placeholder="Ingresar precio de producto"/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-precio">
              <option value="Number" selected>Número</option>
            </select>
          </div>
        </div>
      </div>
      <div id="descripcion">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-descripcion"><strong>*</strong>Descripción:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_descripcion" value="descripcion"/>
            <textarea name="descripcion" id="atr-descripcion" placeholder="Ingresar descripción del producto"></textarea>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-descripcion">
              <option value="String" selected>Texto</option>
            </select>
          </div>
        </div>
      </div>
      <div id="categoria">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-categoria"><strong>*</strong>Categoría:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_categoria" value="categoria"/>
            <input type="text" name="categoria" id="atr-categoria" placeholder="Ingresar categoría de producto"/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-categoria">
              <option value="String" selected>Texto</option>
            </select>
          </div>
        </div>
      </div>
      <div id="almacen">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-en_almacen"><strong>*</strong>Unidades en almacén:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_en_almacen" value="en_almacen"/>
            <input type="text" name="en_almacen" id="atr-en_almacen" placeholder="Ingrese las unidades en almacén"/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-en_almacen">
              <option value="Number" selected>Número</option>
            </select>
          </div>
        </div>
      </div>
      <div id="tipo">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-tipo"><strong>*</strong>Tipo:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_tipo" value="tipo"/>
            <input type="text" name="tipo" id="atr-tipo" placeholder="Ingresar tipo de producto"/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-tipo">
              <option value="String" selected>Texto</option>
            </select>
          </div>
        </div>
      </div>
      <div id="imagenes">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="atr-imagenes"><strong>*</strong>Imagenes:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" hidden="hidden" name="_imagenes" value="imagenes"/>
            <input type="file" name="imagenes" id="atr-imagenes" multiple/>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" name="tipo-imagenes">
              <option value="Binary" selected>Imagenes</option>
            </select>
          </div>
        </div>
      </div>
      <div v-for="(atributo, index) in atributos" :key="index" :id="'atr-' + atributo.nombre">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label :for="'atr-' + atributo.nombre">{{ atributo.nombre }}:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" :name="'_' + atributo.nombre" :id="'atr-' + atributo.nombre" v-model="atributo.nombre" hidden="hidden"/>
            <input
                v-if="atributo.tipo === 'Date'"
                type="date"
                :name="atributo.nombre"
                :id="'atr-' + atributo.nombre"
                v-model="atributo.valor"
                :placeholder="getPlaceholder(atributo.tipo)"
            />
            <input
                v-else-if="atributo.tipo === 'Boolean'"
                type="checkbox"
                value="true"
                :name="atributo.nombre"
                v-model="atributo.valor"
            />
            <input
                v-else
                type="text"
                :name="atributo.nombre"
                :id="'atr-' + atributo.nombre"
                v-model="atributo.valor"
                :placeholder="getPlaceholder(atributo.tipo)"
            />
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <select class="form-select" :name="'tipo-' + atributo.nombre" v-model="atributo.tipo">
              <option :value="tipo" :selected="tipo === 'String'" v-for="tipo in tiposDeMongo" :key="tipo">{{ tipo }}</option>
            </select>
          </div>
        </div>
        <div v-if="!atributo.guardado">
          <div @click="guardarAtributo(index)" class="btn btn-success">Guardar</div>
        </div>
        <div v-else>
          <div @click="editarAtributo(index)" class="btn btn-primary">Editar</div>
          <div @click="eliminarAtributo(index)" class="btn btn-danger">Eliminar</div>
        </div>
      </div>
      <!-- Nuevo Atributo -->
      <div v-if="nuevoAtributoVisible">
        <div class="row align-items-center ">
          <div class="col-md-4 align-items-center text-center ">
            <label for="nuevo-nombre">Nuevo Atributo:</label>
          </div>
          <div class="col-md-4 align-items-center text-center ">
            <input type="text" id="nuevo-nombre" v-model="nuevoAtributo.nombre" placeholder="Nombre del atributo"/>
          </div>
        </div>
        <div>
          <div @click="guardarNuevoAtributo()" class="btn btn-success">Guardar</div>
        </div>
      </div>
      <div id="botones-form" class="row align-items-center  justify-content-center mt-4">
    </div>
      <div @click="guardarProductos()" class="btn btn-success">Guardar producto</div>
      <div @click="limpiarFormulario()" class="btn btn-secondary" v-if="atributos.length !== 0">Quitar todos los atributos</div>
      <div @click="mostrarNuevoAtributo()" v-if="!nuevoAtributoVisible" class="btn btn-primary">Nuevo Atributo</div>
    </div>
  </form>
  </div>
</template>

<style>
.is-invalid{
  color: red;
}
strong{
  color: red;
}
input,textarea{
  width: 500px;
}
</style>