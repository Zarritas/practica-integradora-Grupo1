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
                      <img :src="'data:image/png;base64,'+value.data" alt="prueba">
                        <!-- Muestra el tipo de dato correspondiente -->
                      <small class="text-muted">{{ tiposDeDatos[key] }}</small>
                    </span>
                    <span v-else-if="tiposDeDatos[key]==='Date'">
                      <input :value="formatDate(value)" type="text" class="form-control">
                      <small class="text-muted">{{ tiposDeDatos[key] }}</small>
                    </span>
                    <span v-else>
                      <input :value="producto[key]" type="text" class="form-control">
                        <!-- Muestra el tipo de dato correspondiente -->
                      <small class="text-muted">{{ tiposDeDatos[key] }}</small>
                    </span>
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
      tiposDeDatos:{},
    };
  },

  methods: {
    async fetchProducto(id) {
      try {
        const response = await fetch(`http://172.19.0.1:8080/producto/detalle/${id}`);
        const data = await response.json();
        this.producto = data.documento;
        this.tiposDeDatos = data.tipos_de_datos; // Agrega esta línea para guardar los tipos de datos
      } catch (error) {
        console.error('Error al obtener los productos desde la primera dirección:', error);

        // Si falla la primera solicitud, realizar otra solicitud a una segunda dirección
        try {
          const response = await fetch(`http://172.19.0.3:8080/producto/detalle/${id}`);
          const data = await response.json();
          this.producto = data.documento;
          this.tiposDeDatos = data.tipos_de_datos; // Agrega esta línea para guardar los tipos de datos
        } catch (error) {
          console.error('Error al obtener los productos desde la segunda dirección:', error);
          throw new Error('No se pudieron obtener los productos');
        }
      }
    },
    formatDate(dateString) {
      // Convierte la cadena de fecha a un objeto Date
      const date = new Date(dateString);
      // Formatea la fecha según tus necesidades
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return date.toLocaleDateString('es-ES', options); // Cambia 'es-ES' al código de idioma deseado
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