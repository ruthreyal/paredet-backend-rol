import axios from 'axios';

const API_URL = 'https://backend-production-cfd6.up.railway.app/api/usuarios';

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
