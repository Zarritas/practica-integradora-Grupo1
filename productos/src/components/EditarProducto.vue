<template>
  <div class="container mt-4">
    <h2 class="text-center">Detalles del Producto</h2>
    <div v-if="producto">
      <form enctype="multipart/form-data" id="formulario">
        <div class="modal-body" id="cuerpo-form">
          <div id="nombre">
            <div class="row align-items-center ">
              <div class="col-md-4 align-items-center text-center ">
                <label for="atr-nombre"><strong>*</strong>Nombre:</label>
              </div>
              <div class="col-md-4 align-items-center text-center ">
                <input type="text" hidden="hidden" name="_nombre" value="nombre" />
                <input type="text" name="nombre" id="atr-nombre" :value="this.producto.nombre" placeholder="Ingresar nombre de producto" />
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
                <label for="atr-imagen_perfil"><strong>*</strong>Imagen de Perfil:</label>
              </div>
              <div class="col-md-4 align-items-center text-center ">
                <input type="text" hidden="hidden" name="_imagen_perfil" value="imagen_perfil" />
                <span>
                  <img :src="'data:image/png;base64,'+this.producto.imagen_perfil" alt="prueba" class="card-img-left">
                </span>
                <input type="file" name="imagen_perfil" id="atr-imagen_perfil" />
              </div>
              <div class="col-md-4 align-items-center text-center ">
                <select class="form-select" name="tipo-imagen_perfil">
                  <option value="Binary" selected>Imagen</option>
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
                <input type="text" :name="'_' + atributo.nombre" v-model="atributo.nombre" hidden="hidden"/>
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
              </div>Object.2
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
    <div v-else>
      <p class="text-center">Cargando...</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      id: null,
      producto: null,
      tiposDeDatos : null,
      atributos: [{ key: '', value: '', tipo: '' }],
      tiposDeMongo: ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean'] // Tipos de datos en MongoDB
    };
  },

  methods: {
    mostrarProducto(){
          axios.get(`http://www.poketienda.com/producto/detalle/${this.id}`)
              .then( response =>{
                const data = response.data;
                this.producto = data.documento;
                this.tiposDeDatos = data.tipos_de_datos;
              })
              .catch(error => {
                console.error('Error al obtener los productos desde la primera dirección:', error);
              })
    },
    guardarProductos() {
      const formData = new FormData(document.getElementById('formulario'));
      axios.post(`http://www.poketienda.com/producto/actualizar/${this.id}`, formData)
          .then(response => {
            if (response.data.success) {
              alert(response.data.message);
              console.log(response.data);
              this.editando = false;
              window.location.href = "http://productos.poketienda.com/";
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
    agregarAtributo() {
      this.atributos.push({ key: '', value: '', tipo: '' });
    },
    eliminarAtributo(index) {
      this.atributos.splice(index, 1);
    }
  },
  mounted() {
    this.id = this.$route.params.id; // Obtener el ID del producto de la ruta
    this.mostrarProducto();
  }
};
</script>



<style scoped>

</style>