// src/store/session.js
import { parseDataString } from '@/utils/sesionUtil';

export default {
    namespaced: true,
    state: {
        sessionData: {},
    },
    mutations: {
        setSessionData(state, data) {
            state.sessionData = data;
        },
        clearSession(state) {
            state.sessionData = {};
        },
    },
    actions: {
        initializeSession({ commit }, sessionString) {
            const sessionData = parseDataString(sessionString);
            commit('setSessionData', sessionData);
        },
        clearSession({ commit }) {
            commit('clearSession');
        },
    },
    getters: {
        getSessionData: (state) => state.sessionData,
        getUsuario: (state) => state.sessionData.usuarioLoggeado,
        getAdministrador: (state) => state.sessionData.administradorLoggeado,
        getNumeroPaginasVisitadas: (state) => state.sessionData.numeroPaginasVisitadas,
        // Agrega más getters según tus necesidades
    },
};
