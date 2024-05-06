<script>

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
    nuevoProducto() {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.$router.push({ name: 'CrearProducto'});
    },
    async fetchProductos() {
      try {
        const response = await fetch('http://172.19.0.3:8080/tienda/producto/listado');
        console.log('Conexión establecida en la direccion http://172.19.0.3:8080/tienda/producto/listado')
        let datos = await response.json()
        console.log(datos)
        this.productos = datos
      } catch (error) {
        console.error('Error al obtener los productos desde la dirección http://172.19.0.3:8080/tienda/producto/listado');

        // Si falla la primera solicitud, realizar otra solicitud a una segunda dirección
        try {
          const response = await fetch('http://172.19.0.1:8080/producto/listado');
          console.log('Conexión establecida en la direccion http://172.19.0.1:8080/producto/listado')
          let datos = await response.json()
          console.log(datos)
          this.productos = datos
        } catch (error) {
          console.error('Error al obtener los productos desde la dirección http://172.19.0.1:8080/producto/listado');
          throw new Error('No se pudieron obtener los productos');
        }
      }
    }
  },
  data(){
    return {
      productos:[],
    };
  },
  mounted() {
    this.fetchProductos(); // Llamar al método fetchProductos cuando el componente se monte
  }
};
</script>

<template>
  <div class="home">
    <h1 class="text-center">Productos</h1>
    <button class="btn btn-primary" @click="nuevoProducto()">Nuevo Producto</button>
    <div class="d-flex flex-wrap justify-content-between">
      <div class="card mb-3 position-relative" v-for="producto in productos" :key="producto._id">
        <img class="card-img-top" :src="'data:image/png;base64,'+producto.imagenes.data" :alt="'Imagen '+producto.name">
        <div class="card-body">
          <h5 class="card-title">{{ producto.nombre }}</h5>
          <p class="card-text">{{ producto.descripcion }}</p>
        </div>
        <button class="btn btn-outline-secondary btn-editar position-absolute top-0 end-0 mt-2 mr-2" @click="editarProducto(producto._id)">&#x270d;</button>
        <div class="card-footer d-flex justify-content-between align-items-center">
          <div>
            <button class="btn btn-primary mr-2" v-if="producto.en_almacen > 0">Comprar</button>
            <button class="btn btn-primary mr-2" v-else disabled>No disponible</button>
            <button class="btn btn-secondary mr-2" @click="verDetalles(producto._id)">Más información</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>