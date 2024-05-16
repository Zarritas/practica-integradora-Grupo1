<template>
  <div class="container mt-4">
    <h2 class="text-center">Detalles del Producto</h2>
    <div v-if="producto">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <div class="card">
            <div class="card-body">
              <div class="row" v-for="(value, key) in producto" :key="key">
                <div class="col-sm-4">
                  <label class="font-weight-bold">{{ key }}</label>
                </div>
                <div class="col-sm-8">
                  <span v-if="key === 'image'">
                    <img :src="value" class="img-fluid" alt="Imagen del producto">
                  </span>
                  <span v-else>
                    <input :value="producto[key]" type="text" class="form-control">
                  </span>
                  <select v-model="tiposDeDatos" class="form-select" :name="'tipo '+key">
                    <option
                        v-for="(tipo, index) in tiposDeMongo"
                        :key="index"
                        :value="value">{{ tipo }}</option>
                  </select>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 offset-sm-2">
                  <button class="btn btn-success me-2" @click="guardarProductos">Guardar</button>
                  <button class="btn btn-info me-2" @click="agregarAtributo">Agregar Atributo</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
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