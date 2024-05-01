<script>
export default {
  data(){
    return {
      productos:[],
      loading: true,
    };
  },
  async created() {
    try {
      const response = await fetch('http://172.19.0.3:8080/tienda/producto/listado')
      // const response = await fetch('http://172.19.0.1:8080/producto/listado');
      if (!response.ok) {
        console.log('Error al obtener los datos de la API');
      }
      this.productos = await response.json();
      this.loading = false;
    } catch (error) {
      console.error('Error fetching departments:', error);
    }
  },
};
</script>

<template>
  <div>
    <h1 class="text-center text-muted">Productos</h1>
    <div class="d-flex flex-wrap flex-row">
      <div class="container" v-for="producto in productos" :key="producto.id">
        <img class="imagen" :src="producto.image" alt="imagen patata">
        <div class="atribb">{{ producto.name }}</div>
        <div class="atribb">{{ producto.descripcion }}</div>
        <button class="btn btn-primary" v-if="producto.cantidad > 0">Comprar</button>
        <button class="btn btn-secondary" v-else disabled>No disponible</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.imagen{
  width: 50px;
}
div.producto{
  width: 200px;
  height: 200px;
  font-size: large;
  font-weight: bolder;
  border: 1px black solid;
  box-shadow: 3px 3px 0 black;
  margin: 15px;
}
div.atribb{
  margin: 10px;
}
</style>