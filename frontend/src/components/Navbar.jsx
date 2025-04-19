import React, { useState } from "react";
import { Link } from "react-router-dom";
import "../styles/index.css";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const toggleMenu = () => setIsOpen(!isOpen);

  return (
    <>
      {/* NAVBAR DESKTOP */}
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4 shadow-sm d-none d-lg-flex justify-content-between align-items-center">
        <ul className="navbar-nav d-flex justify-content-evenly flex-grow-1">
          <li className="nav-item">
            <Link className="nav-link" to="#">Papeles Pintados</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="#">Fotomurales</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="#">Colecciones</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="#">La Empresa</Link>
          </li>
        </ul>
        <ul className="navbar-nav align-items-center gap-3 ms-3">
          <li className="nav-item">
            <Link className="nav-link" to="#"><i className="bi bi-search"></i></Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/login"><i className="bi bi-person"></i></Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="#"><i className="bi bi-cart"></i></Link>
          </li>
        </ul>
      </nav>

      {/* NAVBAR MOBILE */}
      <nav className="navbar navbar-dark bg-black d-flex d-lg-none justify-content-between align-items-center px-3 py-2 position-relative">
        <button
          className={`menu-toggle ${isOpen ? "open" : ""}`}
          onClick={toggleMenu}
          aria-label="Toggle menu"
        >
          <span></span>
          <span></span>
          <span></span>
        </button>

        <span className="fw-bold text-uppercase text-white mx-auto logo-text">
          PAREDET
        </span>

        <i className="bi bi-search text-white fs-5"></i>
      </nav>

      {/* MENÚ DESPLEGABLE */}
      <div className={`mobile-menu ${isOpen ? "show" : ""}`}>
        <ul className="navbar-nav px-4 pt-3">
        <li className="nav-item mb-3 d-flex justify-content-end gap-4">
          <Link to="#" className="nav-link p-0 text-white"><i className="bi bi-person fs-5"></i></Link>
          <Link to="#" className="nav-link p-0 text-white"><i className="bi bi-cart fs-5"></i></Link>
        </li>
          <li className="nav-item mb-2">
            <Link className="nav-link text-white" to="#" onClick={toggleMenu}>Papeles Pintados</Link>
          </li>
          <li className="nav-item mb-2">
            <Link className="nav-link text-white" to="#" onClick={toggleMenu}>Fotomurales</Link>
          </li>
          <li className="nav-item mb-2">
            <Link className="nav-link text-white" to="#" onClick={toggleMenu}>Colecciones</Link>
          </li>
          <li className="nav-item mb-2">
            <Link className="nav-link text-white" to="#" onClick={toggleMenu}>La Empresa</Link>
          </li>
          <li className="nav-item mt-4 d-flex gap-4 ps-1">
            <i className="bi bi-search fs-5"></i>
            <i className="bi bi-person fs-5"></i>
            <i className="bi bi-cart fs-5"></i>
          </li>
        </ul>
      </div>
    </>
  );
};

export default Navbar;

















