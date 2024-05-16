<script>
import axios from "axios";

export default {
  name: 'ListaProductos',
  methods: {
    verDetalles(id) {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.$router.push({ name: 'DetalleProducto', params: { id: id } });
    },
    editarProducto(id) {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.$router.push({ name: 'EditarProducto', params: { id: id } });
    },
    async borrarProducto(id) {
      try {
        await axios.delete(`http://www.poketienda.com/producto/borrar-por-id/${id}`);
        alert('Producto borrado correctamente');
      } catch (error) {
        console.error('Error al borrar el producto:', error);
      }finally {
        // window.location.href = "http://productos.poketienda.com"
        window.location.href = "http://172.19.0.18:8080"
      }
    },
    nuevoProducto() {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.$router.push({ name: 'CrearProducto'});
    },
    async fetchProductos() {
      try {
        const response = await fetch('http://www.poketienda.com/producto/listado');
        console.log('Conexión establecida en la direccion http://www.poketienda.com/producto/listado')
        this.productos = await response.json()
      } catch (error) {
        console.error('Error al obtener los productos desde la dirección http://www.poketienda.com/producto/listado');
      }
    }
  },
  data(){
    return {
      productos:[],
    };
  },
  mounted() {
    this.fetchProductos();
  }
};
</script>

<template>
  <div class="home">
    <h1 class="text-center">Productos</h1>
    <button class="btn btn-primary" @click="nuevoProducto()">Nuevo Producto</button>
    <div class="d-flex flex-wrap justify-content-between">
      <div class="card mb-3 position-relative" v-for="producto in productos" :key="producto._id">
          <img class="card card-img-top" :src="'data:image/png;base64,'+producto.imagen_perfil.data" :alt="'Imagen de perfil '+producto.name">
        <div class="card-body">
          <h5 class="card-title">{{ producto.nombre }}</h5>
          <p class="card-text">{{ producto.descripcion }}</p>
        </div>
        <div class="card-footer d-flex justify-content-between align-items-center">
          <div>
            <button class="btn btn-primary mr-2" v-if="producto.en_almacen > 0">Comprar</button>
            <button class="btn btn-primary mr-2" v-else disabled>No disponible</button>
            <button class="btn btn-secondary mr-2" @click="verDetalles(producto._id)">Más información</button>
          </div>
          <div>
            <button class="btn btn-primary mr-2" @click="editarProducto(producto._id)">Editan</button>
            <button class="btn btn-primary mr-2" @click="borrarProducto(producto._id)">Comprar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.card-img-top{
  width: 300px;
}
</style>