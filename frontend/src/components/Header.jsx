import React from "react";
import { Link } from "react-router-dom"; // <-- Añadido
import logo from "../assets/icono.png";

const Header = () => {
  return (
    <header className="bg-dark text-center d-none d-lg-block">
      <Link to="/" className="text-decoration-none text-light">
        <img
          src={logo}
          alt="Logo Paredet"
          style={{ objectFit: "contain" }}
          className="mb-2"
        />
        <h1 className="logo-text">PAREDET</h1>
        <p className="slogan">DISEÑO, ESTILO Y TENDENCIAS</p>
      </Link>
    </header>
  );
};

export default Header;


