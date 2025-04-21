import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL + '/api/usuarios';

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

