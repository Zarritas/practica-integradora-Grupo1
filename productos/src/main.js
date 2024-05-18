import Vue from 'vue';
import App from './App.vue';
import "bootstrap/dist/css/bootstrap.min.css";
import vuetify from './plugins/vuetify';
import 'vuetify/dist/vuetify.min.css';
import router from './router';
import store from './store';
import axios from './plugins/axios'; // Importar configuración de Axios

Vue.config.productionTip = false;

Vue.prototype.$axios = axios; // Añadir Axios al prototipo de Vue

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app');
