<template>
  <div class="home">
    <ProductosTienda/>
  </div>
</template>

<script>
// @ is an alias to /src
import ProductosTienda from "@/components/ProductosTienda.vue";

export default {
  name: 'HomeView',
  components: {
    ProductosTienda
  }
}
</script>
