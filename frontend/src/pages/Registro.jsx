import React, { useState } from "react";
import axios from "axios";
import "../styles/Login.css";
import { FaUserPlus } from "react-icons/fa";
import { useNavigate } from "react-router-dom";

const Registro = () => {
  const [formData, setFormData] = useState({
    nombre: "",
    apellido: "",
    email: "",
    password: "",
    telefono: "",
    pais: "",
    ciudad: "",
    codigoPostal: ""
  });

  const [mensaje, setMensaje] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "https://backend-production-cfd6.up.railway.app/api/auth/register",
        formData
      );

      localStorage.setItem("token", response.data.token);
      setMensaje("Registro exitoso");
      setTimeout(() => navigate("/"), 1500);
    } catch (error) {
      setMensaje("Error al registrar. Verifica los datos.");
    }
  };

  return (
    <main className="container d-flex justify-content-center align-items-center" style={{ minHeight: "80vh", paddingTop: "3rem", paddingBottom: "3rem" }}>
      <section className="login-box" style={{ maxWidth: "500px", width: "100%" }} aria-labelledby="registro">
        <h2 id="registro">Formulario de registro</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="nombre">Nombre *</label>
            <input
              id="nombre"
              name="nombre"
              type="text"
              className="form-control"
              required
              value={formData.nombre}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="apellido">Apellido *</label>
            <input
              id="apellido"
              name="apellido"
              type="text"
              className="form-control"
              required
              value={formData.apellido}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="email">Email *</label>
            <input
              id="email"
              name="email"
              type="email"
              className="form-control"
              required
              value={formData.email}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="password">Contraseña *</label>
            <input
              id="password"
              name="password"
              type="password"
              className="form-control"
              required
              minLength="5"
              value={formData.password}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="telefono">Teléfono *</label>
            <input
              id="telefono"
              name="telefono"
              type="tel"
              className="form-control"
              required
              value={formData.telefono}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="pais">País</label>
            <input
              id="pais"
              name="pais"
              type="text"
              className="form-control"
              value={formData.pais}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="ciudad">Ciudad</label>
            <input
              id="ciudad"
              name="ciudad"
              type="text"
              className="form-control"
              value={formData.ciudad}
              onChange={handleChange}
            />
          </div>

          <div className="mb-3">
            <label htmlFor="codigoPostal">Código Postal</label>
            <input
              id="codigoPostal"
              name="codigoPostal"
              type="text"
              className="form-control"
              value={formData.codigoPostal}
              onChange={handleChange}
            />
          </div>

          <button type="submit" className="btn btn-gold w-100 mt-3">
            <FaUserPlus className="me-2" />
            Registrarse
          </button>
        </form>

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

export default Registro;


