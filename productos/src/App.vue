<template>
  <div id="app">
    <CabeceraPrincipal/>
    <nav class="navbar navbar-light bg-light">
        <router-link to="/" >Home</router-link>|
        <router-link to="/carrito">Carrito</router-link>
    </nav>
    <router-view/>
    <FooterPrincipal/>
  </div>
</template>

<style scoped lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
<script setup lang="ts">
import CabeceraPrincipal from "@/components/CabeceraPrincipal.vue";
import FooterPrincipal from "@/components/FooterPrincipal.vue";
</script>