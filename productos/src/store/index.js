// src/store.js
import Vue from 'vue';
import Vuex from 'vuex';
import axios from '@/plugins/axios'; // Usar la configuración de Axios

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
    isAuthenticated: false
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
      state.isAuthenticated = true;
    },
    clearUser(state) {
      state.user = null;
      state.isAuthenticated = false;
    }
  },
  actions: {
    async fetchUser({ commit }) {
      try {
        const response = await axios.get('/usuario/usuarioActual');
        commit('setUser', response.data.user);
      } catch (error) {
        console.error('Error al obtener el usuario:', error);
        commit('clearUser');
      }
    },
    logout({ commit }) {
      commit('clearUser');
    }
  },
  getters: {
    isAuthenticated: state => state.isAuthenticated,
    user: state => state.user
  }
});
