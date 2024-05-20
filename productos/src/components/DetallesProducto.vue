<template>
  <div class="container mt-4">
    <h2 class="text-center">Detalles del Producto</h2>
    <div v-if="producto">
      <div class="row">
        <div class="col-md-6 offset-md-3">
          <div>
            <div>
              <div v-for="(value, key) in producto" :key="key" class="mb-3">
                <span v-if="key === '_id'"/>
                <div v-else class="row">
                  <div class="col-sm-4 border-black border border-1">
                    <label class="font-weight-bold">{{ key }}</label>
                  </div>
                  <div class="col-sm-8 border-black border border-1">
                    <span v-if="key === 'imagenes'">
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
                    </span>
                    <span v-else-if="key === 'imagen_perfil'">
                        <img :src="'data:image/png;base64,'+value.data" alt="prueba" class="images">
                    </span>
                    <span v-else-if="tiposDeDatos[key]==='Date'">
                      <input :value="formatDate(value)" type="text" class="form-control">
                    </span>
                    <span v-else-if="tiposDeDatos[key]==='Document'">
                      <div v-for="(value,key) in value" :key="value">
                        <span class="col-sm-4">
                          <label class="font-weight-bold">{{ key }}</label>
                        </span>
                        <span>
                          <input :value="value" type="text" class="form-control">
                        </span>
                      </div>
                    </span>
                    <span v-else>
                      <input :value="producto[key]" type="text" class="form-control">
                    </span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <button class="btn btn-primary mr-2" v-if="producto.en_almacen > 0">Comprar</button>
      </div>
    </div>
    <div v-else>
      <p class="text-center">Cargando...</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      id: null,
      producto: null,
      tiposDeDatos: {},
      currentIndex: 0,
    };
  },
  methods: {
    ...mapGetters("session",['getUsuario','getAdministrador']),
    async fetchProducto() {
      await axios.get(`http://www.poketienda.com/producto/detalle/${this.id}`)
          .then(response =>{
            this.producto = response.data.documento
            this.tiposDeDatos = response.data.tipos_de_datos
          })
          .catch(e=>{
            console.error('Error al obtener los productos desde la segunda dirección:', e.mensaje);
            throw new Error('No se pudieron obtener los productos');
          })
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const options = {year: 'numeric', month: '2-digit', day: '2-digit'};
      return date.toLocaleDateString('es-ES', options);
    },
    borrarProducto() {
      try {
        axios.delete(`http://www.poketienda.com/producto/borrar-por-id/${this.id}`);
        alert('Producto borrado correctamente');
      } catch (error) {
        console.error('Error al borrar el producto:', error);
      } finally {
        window.location.href = "http://productos.poketienda.com";
      }
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
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    this.fetchProducto();
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
