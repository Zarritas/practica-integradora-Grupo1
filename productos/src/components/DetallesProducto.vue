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
                    <span v-if="key === 'image'">
                      <img :src="value" class="img-fluid" alt="Imagen del producto">
                    </span>
                    <span v-else>{{ value }}</span>
                  </div>
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
    };
  },

  methods: {
    async fetchProducto(id) {
      try {
        const response = await fetch(`http://172.19.0.1:8080/producto/detalle/${id}`);
        this.producto = await response.json();
      } catch (error) {
        console.error('Error al obtener los productos desde la primera dirección:', error);

        // Si falla la primera solicitud, realizar otra solicitud a una segunda dirección
        try {
          const response = await fetch(`http://172.19.0.3:8080/producto/detalle/${id}`);
          this.producto = await response.json();
        } catch (error) {
          console.error('Error al obtener los productos desde la segunda dirección:', error);
          throw new Error('No se pudieron obtener los productos');
        }
      }
    },
  },
  mounted() {
    const id = this.$route.params.id; // Obtener el ID del producto de la ruta
    this.fetchProducto(id); // Cargar el producto cuando el componente se monte
  }
};
</script>



<style scoped>

</style>