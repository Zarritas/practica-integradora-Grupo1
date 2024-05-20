<template>
  <div class="home">
    <ProductosTienda/>
  </div>
</template>

<script>
import ProductosTienda from "@/components/ProductosTienda.vue";

export default {
  name: 'HomeView',
  components: {
    ProductosTienda
  },
}
</script>
