<script>
import { mapGetters } from "vuex";
import axios from "axios";

export default {
  data(){
    return {
      carrito:null,
    }
  },
  methods:{
    ...mapGetters('session',['getUsuario']),
    async fetchCarrito(){
      await axios.get(`http://www.poketienda.com/producto/carrito/${this.getUsuario.id}`)
          .then(r=>{
            if (!r.data.carrito){
              this.carrito = "No hay nada en el carrito"
            }else{
              this.carrito = r.data.carrito
            }
          })
          .catch(e=>{
            console.error(e)
          })
    },
    verDetalles(id) {
      this.$router.push({ name: 'DetalleProducto', params: { id: id } });
    },
    async borrarProducto(id) {
      try {
        await axios.delete(`http://www.poketienda.com/producto/borrar-producto-carrito/${id}`);
        alert('Producto borrado correctamente');
      } catch (error) {
        console.error('Error al borrar el producto:', error);
      }
    },
    async limpiarCarrito(id){
      try {
        await axios.delete(`http://www.poketienda.com/producto/borrar-carrito/${id}`);
        alert('Producto borrado correctamente');
      } catch (error) {
        console.error('Error al borrar el producto:', error);
      } finally {
        window.location.href = "http://productos.poketienda.com";
      }
    }
  },
  mounted() {
    this.fetchCarrito()
  }
}
</script>

<template>
<div id="cuerpo">
  <div>
    <h1 class="text-center">Carrito</h1>
    <button class="btn btn-primary" @click="limpiarCarrito(this.getUsuario.id)">Limpiar Carrito</button>
  </div>
  <div class="d-flex flex-wrap justify-content-between">
    <div v-for="(key,productos) in this.carrito.productos" :key="key">
      <div class="card mb-3 position-relative" v-for="producto in productos" :key="producto._id">
        <img class="card card-img-top" :src="'data:image/png;base64,'+producto.imagen_perfil.data" :alt="'Imagen de perfil '+producto.name">
        <div class="card-body">
          <h5 class="card-title">{{ producto.nombre }}</h5>
          <p class="card-text">{{ producto.descripcion }}</p>
        </div>
        <div class="card-footer d-flex justify-content-between align-items-center">
          <div>
            <button class="btn btn-secondary mr-2" @click="verDetalles(producto._id)">Más información</button>
          </div>
          <div>
            <button class="btn btn-danger mr-2" @click="borrarProducto(producto._id)">Borrar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<style scoped>

</style>