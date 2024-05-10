<template>
  <div class="container mt-4">
    <h2 class="text-center">Detalles del Producto</h2>
    <div v-if="producto">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <div class="card">
            <div class="card-body">
              <div v-for="(value, key) in producto" :key="key" class="mb-3">
                <div class="row">
                  <div class="col-sm-4">
                    <label class="font-weight-bold">{{ key }}</label>
                  </div>
                  <div class="col-sm-8">
                    <span v-if="key === 'imagenes'">
                      <span v-for="(data,key) in value" :key="key">
                        <img :src="'data:image/png;base64,'+data.data" alt="prueba" class="card-img">
                      </span>
                    </span>
                    <span v-else-if="key === 'imagen_perfil'">
                        <img :src="'data:image/png;base64,'+value.data" alt="prueba" class="card-img-left">
                    </span>
                    <span v-else-if="tiposDeDatos[key]==='Date'">
                      <input :value="formatDate(value)" type="text" class="form-control">
                      <small class="text-muted">{{ tiposDeDatos[key] }}</small>
                    </span>
                    <span v-else>
                      <input :value="producto[key]" type="text" class="form-control">
                      <small class="text-muted">{{ tiposDeDatos[key] }}</small>
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div @click="borrarProducto()" class="btn btn-danger">Borrar producto</div>
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
      producto: null,
      tiposDeDatos:{},
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
        console.error('Error al obtener los productos desde la segunda dirección:', error);
        throw new Error('No se pudieron obtener los productos');
      }
    },
    formatDate(dateString) {
      const date = new Date(dateString);

      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return date.toLocaleDateString('es-ES', options);
    },
    async borrarProducto() {
      const id = this.$route.params.id;
      try {
        await axios.delete(`http://www.poketienda.com/producto/borrar-por-id/${id}`);
        alert('Producto borrado correctamente');
      } catch (error) {
        console.error('Error al borrar el producto:', error);
      }finally {
        // window.location.href = "http://productos.poketienda.com"
        window.location.href = "http://172.19.0.18:8080"
      }
    }
  },
  mounted() {
    const id = this.$route.params.id;
    this.fetchProducto(id);
  }
};
</script>



<style scoped>

</style>