import axios from 'axios';

const API_URL = 'http://localhost:8080/direcciones';

const direccionService = {
  getDirecciones: () => axios.get(API_URL),

  getDireccionById: (id) => axios.get(`${API_URL}/${id}`),

  crearDireccion: (direccion) => axios.post(API_URL, direccion),

  actualizarDireccion: (id, direccion) => axios.put(`${API_URL}/${id}`, direccion),

  eliminarDireccion: (id) => axios.delete(`${API_URL}/${id}`),
};

export default direccionService;

