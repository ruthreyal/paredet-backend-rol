import axios from 'axios';

const API_URL = 'http://localhost:8080/api/usuarios'; // 🔄 LOCAL

const getUsuarioPorEmail = async (email, token) => {
  const response = await axios.get(`${API_URL}/email/${email}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

const usuarioService = {
  getUsuarioPorEmail,
};

export default usuarioService;

