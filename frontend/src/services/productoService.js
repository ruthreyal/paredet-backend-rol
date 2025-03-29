import axios from 'axios';

const API_URL = 'http://localhost:8080'; // o la URL de Railway si ya está desplegado

const getAllProductos = () => {
  return axios.get(`${API_URL}/productos`);
};

export default {
  getAllProductos,
};
