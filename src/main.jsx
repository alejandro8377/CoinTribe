import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import "./styles/base.css";

import Portada from "./pages/Portada";
import Login from "./pages/Login";
import CrearCuenta from "./pages/CrearCuenta";
import Inicio from "./pages/Inicio";
import Cursos from "./pages/Cursos";
import Retos from "./pages/Retos";
import Comunidad from "./pages/Comunidad";
import Perfil from "./pages/Perfil";
import Configuracion from "./pages/Configuracion";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Portada />} />
        <Route path="/login" element={<Login />} />
        <Route path="/crear-cuenta" element={<CrearCuenta />} />
        <Route path="/inicio" element={<Inicio />} />
        <Route path="/cursos" element={<Cursos />} />
        <Route path="/retos" element={<Retos />} />
        <Route path="/comunidad" element={<Comunidad />} />
        <Route path="/perfil" element={<Perfil />} />
        <Route path="/configuracion" element={<Configuracion />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>,
);
