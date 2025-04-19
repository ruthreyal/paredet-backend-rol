import axios from "axios";

const API_URL = "https://backend-production-cfd6.up.railway.app/api/auth/register";

const register = async (usuario) => {
  const response = await axios.post(API_URL, usuario);
  return response.data.token;
};

const registroService = {
  register,
};

export default registroService;
