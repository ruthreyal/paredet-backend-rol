import React, { useState } from "react";
import authService from "../services/authService";
import { useNavigate } from "react-router-dom";
import "../styles/Login.css";
import { FaUserPlus, FaLock } from "react-icons/fa";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [mensaje, setMensaje] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const token = await authService.login(email, password);
      localStorage.setItem("token", token);
      setMensaje("¡Login exitoso!");
      setTimeout(() => navigate("/"), 1000);
    } catch (error) {
      setMensaje("Credenciales incorrectas");
    }
  };

  return (
    <main className="container login-container" role="main" aria-label="Página de identificación">
      <section className="login-box d-flex flex-column justify-content-between" aria-labelledby="crear-cuenta">
        <div>
          <h2 id="crear-cuenta">Crea una cuenta</h2>
          <p>
            Crea una nueva cuenta de manera gratuita rellenando un sencillo
            formulario para poder disfrutar de todas las ventajas de Mi Cuenta
            Paredet.
          </p>
        </div>
        <button
          className="btn btn-dark crear-cuenta-boton"
          aria-label="Crear una cuenta"
          onClick={() => navigate("/registro")}
        >

          <FaUserPlus className="me-2" />
          Crea una cuenta
        </button>
      </section>

      <section className="login-box d-flex flex-column justify-content-between" aria-labelledby="tengo-cuenta">
        <div>
          <h2 id="tengo-cuenta">Tengo cuenta</h2>
          <form onSubmit={handleLogin} aria-describedby="form-login">
            <div className="mb-3">
              <label htmlFor="email" className="form-label">
                Email
              </label>
              <input
                id="email"
                name="email"
                type="email"
                className="form-control"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                aria-required="true"
              />
            </div>
            <div className="mb-3">
              <label htmlFor="password" className="form-label">
                Contraseña
              </label>
              <input
                id="password"
                name="password"
                type="password"
                className="form-control"
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                aria-required="true"
              />
            </div>
            <p className="forgot-password">¿Olvidaste tu contraseña?</p>
            <button type="submit" className="btn btn-outline-dark w-100 mt-2" aria-label="Entrar">
              <FaLock className="me-2" />
              Entrar
            </button>
          </form>
        </div>
        {mensaje && (
          <div
            className={`alert mt-3 ${
              mensaje.includes("exitoso") ? "alert-success" : "alert-danger"
            }`}
            role="alert"
          >
            {mensaje}
          </div>
        )}
      </section>
    </main>
  );
};

export default Login;





  