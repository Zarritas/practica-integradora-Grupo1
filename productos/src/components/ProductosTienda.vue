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
      const response = await fetch('http://localhost:8080/producto/listado');
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
    <h1>Productos</h1>
    <div class="productos">
      <div class="producto" v-for="producto in productos" :key="producto.id">
        <img :src="producto.image">
        <div class="atribb">{{ producto.name }}</div>
        <div class="atribb">{{ producto.date }}</div>
        <button class="atribb" v-if="producto.cantidad > 0">Comprar</button>
        <button class="atribb" v-else disabled>No disponible</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
div.productos{
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
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