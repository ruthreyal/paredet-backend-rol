import { createContext, useState, useEffect } from "react";
import usuarioService from "../services/usuarioService";

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [usuario, setUsuario] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || null);

  useEffect(() => {
    const cargarUsuario = async () => {
      if (token) {
        try {
          const email = JSON.parse(atob(token.split(".")[1])).sub;
          const data = await usuarioService.getUsuarioPorEmail(email, token);
          setUsuario(data);
        } catch (error) {
          console.error("Error cargando usuario desde AuthContext", error);
          logout();
        }
      }
    };
    cargarUsuario();
  }, [token]);

  const login = (nuevoToken) => {
    localStorage.setItem("token", nuevoToken);
    setToken(nuevoToken);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
    setUsuario(null);
  };

  return (
    <AuthContext.Provider value={{ usuario, token, login, logout, isAuthenticated: !!usuario }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
