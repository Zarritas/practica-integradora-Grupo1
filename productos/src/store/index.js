// src/store/index.js
import Vue from 'vue';
import Vuex from 'vuex';
import session from './sesion'; // Importa el módulo de sesión

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    session,
  },
});
