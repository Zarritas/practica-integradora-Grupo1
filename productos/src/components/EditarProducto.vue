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
                    <option value="">-- Selecciona un tipo --</option>
                    <option v-for="(tipo, index) in tiposDeMongo" :key="index" :value="value">{{ tipo }}</option>
                  </select>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-8 offset-sm-2">
                  <button class="btn btn-success me-2" @click="guardarEdicion">Guardar</button>
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
export default {
  data() {
    return {
      producto: null,
      tiposDeDatos : null,
      atributos: [{ key: '', value: '', tipo: '' }],
      tiposDeMongo: ['String', 'Number', 'Date', 'Array', 'Object', 'Boolean'] // Tipos de datos en MongoDB
    };
  },

  methods: {
    async fetchProducto(id) {
      try {
        const response = await fetch(`http://www.poketienda.com/producto/detalle/${id}`);
        const data = await response.json();
        this.producto = data.documento;
        this.tiposDeDatos = data.tipos_de_datos;
      } catch (error) {
        console.error('Error al obtener los productos desde la primera dirección:', error);
      }
    },
    guardarEdicion() {
      // Lógica para guardar los cambios
      this.producto[this.nuevoAtributo.nombre] = this.nuevoAtributo.valor+";"+this.nuevoAtributo.tipo;
      this.atributos.nombre = '';
      this.atributos.valor = '';
      this.atributos.tipo = '';
    },
    agregarAtributo() {
      this.atributos.push({ key: '', value: '', tipo: '' });
    },
    eliminarAtributo(index) {
      this.atributos.splice(index, 1);
    }
  },
  mounted() {
    const id = this.$route.params.id; // Obtener el ID del producto de la ruta
    this.fetchProducto(id); // Cargar el producto cuando el componente se monte
  }
};
</script>



<style scoped>

</style>