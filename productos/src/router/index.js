import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'ListadoProductos',
    component:()=> import('@/components/ProductosTienda.vue')
  },
  {
    path: '/producto/detalles/:id',
    name: 'DetalleProducto',
    component:() => import('@/components/DetallesProducto.vue')

  },
  {
    path: '/producto/editar/:id',
    name: 'EditarProducto',
    component:() => import('@/components/EditarProducto.vue')
  },
  {
    path: '/producto/nuevo',
    name: 'CrearProducto',
    component:() => import('@/components/CrearProducto.vue')
  },
]

const router = new VueRouter({
  routes
})

export default router
