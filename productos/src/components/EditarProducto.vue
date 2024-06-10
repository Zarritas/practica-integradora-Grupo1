<template>
  <div class="container mt-4">
    <h2 class="text-center">Editar Producto</h2>
    <div v-if="producto">
      <form enctype="multipart/form-data" id="formulario">
        <div class="modal-body" id="cuerpo-form">
          <div v-for="(value, key) in this.producto" :key="key" class="mb-3">
            <div v-if="key === '_id' ||key === 'fecha_creacion' ||key === 'fecha_ultima_modificacion'"></div>

            <div id="nombre" v-else-if="key === 'nombre'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-nombre"><strong>*</strong>Nombre:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_nombre" value="nombre" />
                  <input type="text" name="nombre" id="atr-nombre" :value="value" placeholder="Ingresar nombre de producto" />
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-nombre">
                    <option value="String" selected>Texto</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="imagen_perfil" v-else-if="key === 'imagen_perfil'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-imagen_perfil"><strong>*</strong>Imagen de Perfil:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_imagen_perfil" value="imagen_perfil" />
                  <span>
                    <img :src="'data:image/png;base64,'+value.data" alt="prueba" class="images">
                  </span>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-imagen_perfil">
                    <option value="Binary" selected>Imagen</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="precio" v-else-if="key === 'precio'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-precio"><strong>*</strong>Precio:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_precio" value="precio"/>
                  <input type="text" name="precio" id="atr-precio" :value="value" placeholder="Ingresar precio de producto"/>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-precio">
                    <option value="Number" selected>Número</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="descripcion" v-else-if="key === 'descripcion'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-descripcion"><strong>*</strong>Descripción:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_descripcion" value="descripcion"/>
                  <textarea name="descripcion" id="atr-descripcion" placeholder="Ingresar descripción del producto" :value="value"></textarea>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-descripcion">
                    <option value="String" selected>Texto</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="categoria" v-else-if="key === 'categoria'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-categoria"><strong>*</strong>Categoría:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_categoria" value="categoria"/>
                  <input type="text" name="categoria" id="atr-categoria" :value="value" placeholder="Ingresar categoría de producto"/>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-categoria">
                    <option value="String" selected>Texto</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="en_almacen" v-else-if="key === 'en_almacen'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-en_almacen"><strong>*</strong>Unidades en almacén:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_en_almacen" value="en_almacen"/>
                  <input type="text" name="en_almacen" id="atr-en_almacen" :value="value" placeholder="Ingrese las unidades en almacén"/>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-en_almacen">
                    <option value="Number" selected>Número</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="tipo" v-else-if="key === 'tipo'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label :for="'atr-'+key"><strong>*</strong>Tipo:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" :name="'_'+key" :value="key"/>
                  <input type="text" :name="key" :id="'atr'+key" :value="value" placeholder="Ingresar tipo de producto"/>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" :name="'tipo-'+key">
                    <option value="String" selected>Texto</option>
                  </select>
                </div>
              </div>
            </div>

            <div id="imagenes" v-else-if="key === 'imagenes'">
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center ">
                  <label for="atr-imagenes"><strong>*</strong>Imagenes:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" name="_imagenes" value="imagenes"/>
                  <div id="gallery">
                      <div class="gallery">
                        <div class="carousel">
                          <div v-for="(imagen, index) in value" :key="index" :class="{ 'carousel-item': true, 'active': index === currentIndex }">
                            <img class="images" :src="'data:image/png;base64,' + imagen.data" alt="Imagen del producto">
                          </div>
                        </div>
                        <div class="botones">
                          <button class="prev" @click="prevSlide">&#10094;</button>
                          <button class="next" @click="nextSlide">&#10095;</button>
                        </div>
                      </div>
                    </div>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" name="tipo-imagenes">
                    <option value="Binary" selected>Imagenes</option>
                  </select>
                </div>
              </div>
            </div>

            <div :id="key" v-else >
              <div class="row align-items-center ">
                <div class="col-md-4 align-items-center text-center">
                  <label for="atr-imagenes">{{key}}:</label>
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <input type="text" hidden="hidden" :name="'_'+key" :value="key"/>
                  <input :name="key" :id="'atr'+key" v-if="tiposDeDatos[key] === 'Document'" :value="formatearObjeto(value)">
                  <input :name="key" :id="'atr'+key" v-else-if="tiposDeDatos[key] === 'Boolean'" type="checkbox" :checked="value">
                  <input :name="key" :id="'atr'+key" v-else :value="producto[key]" type="text">
                </div>
                <div class="col-md-4 align-items-center text-center ">
                  <select class="form-select" :name="'tipo-' + key" v-model="tiposDeDatos[key]">
                    <option v-for="tipo in tiposDeMongo" :key="tipo" :value="tipo">
                      {{ tipo }}
                    </option>
                  </select>
                </div>
              </div>
            </div>

          </div>
        </div>
        <div>
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
    <div v-else>
      <p class="text-center">Cargando...</p>
    </div>
  </div>
</template>

<script>
import axios from "@/plugins/axios";

export default {
  data() {
    return {
      producto:null,
      tiposDeDatos:{},
      editando: true,
      tiposDeMongo: ['String', 'Number', 'Date', 'ArrayList', 'Document', 'Boolean'],
      atributos: [], // Array para almacenar los atributos
      nuevoAtributo: { nombre: '', tipo: '', valor: '', guardado: false }, // Nuevo atributo
      nuevoAtributoVisible: false, // Controla la visibilidad del formulario para nuevo atributo
      currentIndex: 0,
    };
  },
  methods: {
    async mostrarProducto(){
          await axios.get(`http://www.poketienda.com/producto/detalle/${this.id}`)
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
            alert(response.data.message);
            console.log(response.data);
            this.editando = false;
            this.$router.push({name: 'ListadoProductos'});
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
        case 'ArrayList':
          return 'cosa1,cosa2,cosa3,...'
        case 'Document':
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
    prevSlide() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      }
    },
    nextSlide() {
      if (this.currentIndex < this.producto.imagenes.length - 1) {
        this.currentIndex++;
      } else {
        this.currentIndex = 0;
      }
    },
    formatearObjeto(valores){
      let texto ="";
      for (const [key, value] of Object.entries(valores)) { // Usar Object.entries para iterar sobre el objeto
        texto += key + ":" + value + ",";
      }
      if (texto.endsWith(",")) {
        texto = texto.slice(0, -1);
      }
      return texto;
    }
  },
  mounted() {
    this.id = this.$route.params.id; // Obtener el ID del producto de la ruta
    this.mostrarProducto();
  }
};
</script>

<style scoped>
.gallery {
  position: relative;
  overflow: hidden;
  flex-direction: column;
}
.carousel {
  display: flex;
  transition: transform 0.5s ease;
}
.carousel-item{
  flex: 0 0 33.33%;
}
.images{
  width: 80px;
}
button.prev,
button.next {
  background-color: transparent;
  border: none;
  cursor: pointer;
}
.botones{
  display: flex;
  justify-content: center;
}
input{
  width: 100%;
}
</style>