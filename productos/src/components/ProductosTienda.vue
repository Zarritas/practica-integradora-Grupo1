<script>
import axios from "@/plugins/axios";
import { mapActions, mapGetters, mapState } from "vuex";

export default {
  name: 'ListaProductos',
  data() {
    return {
      productos: [],
      Admin: true,
      sesion: null,
    };
  },
  methods: {
    ...mapActions('session', ['initializeSession']),
    verDetalles(id) {
      this.$router.push({ name: 'DetalleProducto', params: { id: id } });
    },
    editarProducto(id) {
      this.$router.push({ name: 'EditarProducto', params: { id: id } });
    },
    async borrarProducto(id) {
      try {
        await axios.delete(`http://www.poketienda.com/producto/borrar-por-id/${id}`);
        alert('Producto borrado correctamente');
      } catch (error) {
        console.error('Error al borrar el producto:', error);
      } finally {
        window.location.href = "http://172.19.0.18:8080";
      }
    },
    nuevoProducto() {
      this.$router.push({ name: 'CrearProducto' });
    },
    async fetchProductos() {
      try {
        const response = await fetch('http://www.poketienda.com/producto/listado');
        console.log('Conexión establecida en la direccion http://www.poketienda.com/producto/listado');
        document.getElementById("buscar_por_nombre").value = "";
        this.productos = await response.json();
      } catch (error) {
        console.error('Error al obtener los productos desde la dirección http://www.poketienda.com/producto/listado');
      }
    },
    buscarProductos() {
      const formData = new FormData(document.getElementById("formularioBusqueda"));
      axios.post("http://www.poketienda.com/producto/listado-filtrado", formData)
          .then(r => {
            this.productos = r.data;
            console.log('datos cogidos');
          })
          .catch(e => {
            console.error(e.data);
          });
    },
    calcularPrecioMinimo() {
      if (this.productos.length > 0) {
        const precios = this.productos.map(producto => producto.precio);
        return Math.min(...precios);
      } else {
        return '';
      }
    },
    calcularPrecioMaximo() {
      if (this.productos.length > 0) {
        const precios = this.productos.map(producto => producto.precio);
        return Math.max(...precios);
      } else {
        return '';
      }
    }
  },
  computed: {
    ...mapGetters('session', ['getSessionData', 'getUsuario','getAdministrador','getNumeroPaginasVisitadas']),
    ...mapState('session', ['sessionData']),
  },
  mounted() {
    this.fetchProductos();
  },
  // eslint-disable-next-line no-unused-vars
  created() {
    const sessionString = this.$route.query.session;
    if (sessionString) {
      this.initializeSession(sessionString);
    }
    console.log("session = "+sessionString);
  }
};
</script>


<template>
  <div class="home">
    <h1 class="text-center">Productos</h1>
    <button v-if="getAdministrador" class="btn btn-primary" @click="nuevoProducto()">Nuevo Producto</button>
    <div id="contenedor busqueda">
      <form id="formularioBusqueda" class="form-control">
        <div id="nombre">
          <h4>Nombre</h4>
          <input name="nombre" id="buscar_por_nombre" type="text" placeholder="Ingresa el nombre del producto"/>
        </div>
        <div id="precio">
          <h4>Precio</h4>
          <label for="precio_minimo">Desde</label><input name="precio_minimo" id="precio_minimo" type="text" :value="calcularPrecioMinimo()" placeholder="Ingresa el menor"/>

          <label for="precio_maximo">hasta</label> <input name="precio_maximo" id="precio_maximo" type="text" :value="calcularPrecioMaximo()" placeholder="Ingresa el mayor"/>
        </div>
        <div @click="buscarProductos" class="btn btn-outline-primary">Buscar</div>
        <div @click="fetchProductos" class="btn btn-outline-danger">Borrar filtros</div>
      </form>
    </div>
    <div class="d-flex flex-wrap justify-content-between">
      <div class="card mb-3 position-relative" v-for="producto in productos" :key="producto._id">
        <img class="card card-img-top" :src="'data:image/png;base64,'+producto.imagen_perfil.data" :alt="'Imagen de perfil '+producto.name">
        <div class="card-body">
          <h5 class="card-title">{{ producto.nombre }}</h5>
          <p class="card-text">{{ producto.descripcion }}</p>
        </div>
        <div class="card-footer d-flex justify-content-between align-items-center">
          <div>
            <button class="btn btn-primary mr-2" v-if="producto.en_almacen > 0 && getUsuario">Comprar</button>
            <button class="btn btn-primary mr-2" v-else-if="getUsuario" disabled>No disponible</button>
            <button class="btn btn-secondary mr-2" @click="verDetalles(producto._id)">Más información</button>
          </div>
          <div v-if="getAdministrador">
            <button class="btn btn-primary mr-2" @click="editarProducto(producto._id)">Editar</button>
            <button class="btn btn-danger mr-2" @click="borrarProducto(producto._id)">Borrar</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style>
.card-img-top {
  width: 300px;
}
.card {
  max-width: 300px;
}
</style>
