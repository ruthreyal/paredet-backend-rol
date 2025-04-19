import axios from "axios";

const API_URL = "https://backend-production-cfd6.up.railway.app/api/auth/login";

const login = async (email, password) => {
  const response = await axios.post(API_URL, {
    email,
    password,
  });
  return response.data.token;
};

const authService = {
  login,
};

export default authService;


