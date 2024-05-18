// src/router.js
import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'ListadoProductos',
    component: () => import('@/components/ProductosTienda.vue')
  },
  {
    path: '/producto/detalles/:id',
    name: 'DetalleProducto',
    component: () => import('@/components/DetallesProducto.vue')
  },
  {
    path: '/producto/editar/:id',
    name: 'EditarProducto',
    component: () => import('@/components/EditarProducto.vue'),
    meta: { requiresAuth: true }  // Ruta protegida
  },
  {
    path: '/producto/nuevo',
    name: 'CrearProducto',
    component: () => import('@/components/CrearProducto.vue'),
    meta: { requiresAuth: true }  // Ruta protegida
  },
  {
    path: '/carrito',
    name: 'Carrito',
    component: () => import('@/components/Carrito.vue'),
    meta: { requiresAuth: true }  // Ruta protegida
  },
];

const router = new VueRouter({
  routes
});

// Guardián de navegación para proteger rutas
router.beforeEach(async (to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    try {
      // Hacer una solicitud para obtener el usuario actual
      await store.dispatch('fetchUser');

      // Si el usuario está autenticado, deja que la navegación continúe
      if (store.getters.isAuthenticated) {
        next();
      } else {
        // Si no está autenticado, redirige al carrito (o a donde desees)
        next({ name: 'ListadoProductos' });
      }
    } catch (error) {
      console.error('Error al obtener el usuario:', error);
      // Si hay un error, redirige al carrito (o a donde desees)
      next({ name: 'ListaProductos' });
    }
  } else {
    // Si la ruta no requiere autenticación, deja que la navegación continúe
    next();
  }
});

export default router;
