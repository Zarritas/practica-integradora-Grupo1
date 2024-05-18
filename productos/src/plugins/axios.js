// src/plugins/axios.js
import axios from 'axios';

axios.defaults.withCredentials = true;
axios.defaults.baseURL = 'http://www.poketienda.com';

export default axios;
