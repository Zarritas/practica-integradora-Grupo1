// src/store/index.js
import Vue from 'vue';
import Vuex from 'vuex';
import axios from '@/plugins/axios';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user: null,
  },
  getters: {
    isAuthenticated: state => !!state.user,
    getUser: state => state.user,
  },
  mutations: {
    setUser(state, user) {
      state.user = user;
    },
    clearUser(state) {
      state.user = null;
    },
  },
  actions: {
    async fetchUser({ commit }) {
      try {
        const response = await axios.get('/api/sesion/usuarioActual');
        commit('setUser', response.data);
      } catch (error) {
        console.error('Error al obtener el usuario:', error);
        commit('clearUser');
      }
    },
  },
  modules: {},
});
