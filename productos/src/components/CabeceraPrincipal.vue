<template>
  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top">
      <a class="navbar-brand" href="http://www.poketienda.com/usuario/area-personal">
        <img src="http://www.poketienda.com/images/pokelogo.png" width="30em" class="avatar" alt="Logo Poketienda">
        <span>Poketienda</span>
      </a>
      <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
      </button>
      <!-- Collection of nav links, forms, and other content for toggling -->
      <div id="navbarCollapse" class="collapse navbar-collapse justify-content-start">
        <div class="navbar-nav">
          <a href="http://www.poketienda.com/tienda/area-personal" class="nav-item nav-link">Área personal</a>
          <a href="http://productos.poketienda.com/#/"  class="nav-item nav-link">Productos</a>
        </div>
<!--        <span v-if="usuarioLogged == null">-->
<!--          <div class="navbar-nav ml-auto action-buttons">-->
<!--            <div class="nav-item dropdown">-->
<!--              <a href="http://www.poketienda.com/usuario/authusuario"-->
<!--                 class="nav-link mr-4">Iniciar sesión</a>-->
<!--            </div>-->
<!--            <div class="nav-item dropdown">-->
<!--              <a href="http://www.poketienda.com/usuario/registro"-->
<!--                 class="btn btn-primary sign-up-btn">Registrarse</a>-->
<!--            </div>-->
<!--          </div>-->
<!--        </span>-->
<!--        <span v-else>-->
<!--          <div class="navbar-nav ml-auto">-->
<!--            <a href="#" class="nav-item nav-link notifications">-->
<!--              <i class="fa fa-window-maximize"></i><span class="badge" th:text="${numPaginas}"></span></a>-->
<!--            &lt;!&ndash;bi bi-window&ndash;&gt;-->
<!--            <div class="nav-item dropdown">-->
<!--              <a href="#" data-toggle="dropdown" class="nav-link dropdown-toggle user-action">-->
<!--                <img src="http://www.poketienda.com/videos/mapache-pedro-mapache.gif" class="avatar" alt="Avatar">-->
<!--&lt;!&ndash;                <span>{{usuarioLogged}}</span>&ndash;&gt;-->
<!--              </a>-->
<!--              <div class="dropdown-menu">-->
<!--                <a href="http://www.poketienda.com/tienda/area-personal" class="dropdown-item"><i class="fa fa-user-o"></i>-->
<!--                  <span>Perfil</span>-->
<!--                </a>-->
<!--                <a href="#" class="dropdown-item"><i class="fa fa-sliders"></i>-->
<!--                  <span>Ajustes</span>-->
<!--                </a>-->
<!--                <div class="dropdown-divider"></div>-->
<!--                <a href="http://www.poketienda.com/tienda/desconexion" class="dropdown-item"><i class="material-icons">&#xE8AC;</i>-->
<!--                  <span>Desconectar</span>-->
<!--                </a>-->
<!--              </div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </span>-->
      </div>
    </nav>
  </div>
</template>
<script>

</script>
<style>

</style>