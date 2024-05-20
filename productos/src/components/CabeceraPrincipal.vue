<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
    <a @click="this.clearSession" class="navbar-brand" href="http://www.poketienda.com/tienda/area-personal">
      <img src="http://www.poketienda.com/images/pokelogo.png" width="30em" class="avatar" alt="Logo Poketienda">
      <span>Poketienda</span>
    </a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
      <div class="navbar-nav">
        <a @click="this.clearSession" href="http://www.poketienda.com/tienda/area-personal" class="nav-item nav-link">Área personal</a>
        <a href="http://productos.poketienda.com/#/" class="nav-item nav-link">Productos</a>
      </div>
      <div v-if="getAdministrador">
      </div>
      <div v-else-if="!this.getUsuario">
        <div class="navbar-nav ml-auto action-buttons">
          <div class="nav-item dropdown">
            <a @click="this.clearSession" href="http://www.poketienda.com/usuario/authusuario" class="nav-link mr-4">Iniciar sesión</a>
          </div>
          <div class="nav-item dropdown">
            <a @click="this.clearSession" href="http://www.poketienda.com/usuario/registro" class="btn btn-primary sign-up-btn">Registrarse</a>
          </div>
        </div>
      </div>
      <div v-else>
        <div class="navbar-nav ml-auto">
          <a href="#" class="nav-item nav-link notifications">
            <i class="fa fa-window-maximize"></i><span class="badge">{{ numPaginas }}</span>
          </a>
          <div class="nav-item dropdown">
            <a href="#" data-toggle="dropdown" class="nav-link dropdown-toggle user-action">
              <img src="http://www.poketienda.com/videos/mapache-pedro-mapache.gif" class="avatar" alt="Avatar">
              <span>{{ usuarioLogged }}</span>
            </a>
            <div class="dropdown-menu">
              <a @click="this.clearSession" href="http://www.poketienda.com/tienda/area-personal" class="dropdown-item">
                <i class="fa fa-user-o"></i><span>Perfil</span>
              </a>
              <a @click="this.clearSession" href="http://www.poketienda.com/usuario/ajustes" class="dropdown-item">
                <i class="fa fa-sliders"></i><span>Ajustes</span>
              </a>
              <div class="dropdown-divider"></div>
              <a @click="this.clearSession" href="http://www.poketienda.com/usuario/desconexion" class="dropdown-item">
                <i class="material-icons">&#xE8AC;</i><span>Desconectar</span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>
<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'CabeceraPrincipal',
  computed: {
    ...mapGetters('session', ['getUsuario', 'getNumeroPaginasVisitadas','getAdministrador']),
    ...mapActions('session', ['clearSession']),
  },
};
</script>
<style>
.navbar-brand {
  display: flex;
  align-items: center;
}
.navbar-brand .avatar {
  margin-right: 10px;
}
.navbar-nav .nav-link {
  margin-right: 10px;
}
.navbar-nav .sign-up-btn {
  margin-left: 10px;
}
.navbar-nav .user-action .avatar {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}
.navbar-nav .notifications .badge {
  background-color: red;
  color: white;
  position: absolute;
  top: 0;
  right: 0;
  font-size: 12px;
}
</style>
