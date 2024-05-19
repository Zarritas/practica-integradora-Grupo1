// src/plugins/axios.js
import axios from 'axios';

const instance = axios.create({
    baseURL: 'http://www.poketienda.com',
    withCredentials: true,
});

export default instance;
