/* CoinTribe · piezas de interfaz que se repiten en varias pantallas */

import { Link, NavLink } from "react-router-dom";
import { iniciales } from "../utils/formato";
import { usuario } from "../data/content";

export function Marca({ to = "/" }) {
  return (
    <Link to={to} className="ct-marca" title="Portada del proyecto">
      <span className="ct-marca__moneda">CT</span>
      <span className="ct-marca__nombre">CoinTribe</span>
    </Link>
  );
}

export function Avatar({ nombre = usuario.nombre, tamano = "" }) {
  return (
    <span className={"ct-avatar" + (tamano ? " ct-avatar--" + tamano : "")} title={nombre}>
      {iniciales(nombre)}
    </span>
  );
}

const SECCIONES = [
  { to: "/inicio", texto: "Inicio" },
  { to: "/cursos", texto: "Cursos" },
  { to: "/retos", texto: "Retos" },
  { to: "/comunidad", texto: "Comunidad" },
  { to: "/perfil", texto: "Perfil" },
];

export function Topbar() {
  return (
    <header className="ct-topbar">
      <Marca />

      <nav className="ct-nav" aria-label="Secciones de CoinTribe">
        {SECCIONES.map((seccion) => (
          <NavLink
            key={seccion.to}
            to={seccion.to}
            className={({ isActive }) =>
              "ct-nav__link" + (isActive ? " ct-nav__link--activa" : "")
            }
          >
            {seccion.texto}
          </NavLink>
        ))}
      </nav>

      <div className="ct-topbar__acciones">
        <Link className="ct-icon-btn" to="/configuracion" aria-label="Configuración">
          ⚙
        </Link>
        <Link to="/perfil" aria-label="Tu perfil">
          <Avatar />
        </Link>
      </div>
    </header>
  );
}

/* Riel de monedas: cada moneda equivale al 10% de la meta. */
export function RielMonedas({ avance = 0, piezas = 10 }) {
  const llenas = (Math.max(0, Math.min(100, avance)) / 100) * piezas;

  return (
    <div className="ct-monedas">
      {Array.from({ length: piezas }, (_, i) => {
        let clase = "ct-monedas__pieza";
        const estilo = {};

        if (i + 1 <= Math.floor(llenas)) {
          clase += " ct-monedas__pieza--llena";
        } else if (i < llenas) {
          clase += " ct-monedas__pieza--parcial";
          estilo["--ct-parcial"] = (llenas - i) * 100 + "%";
        }

        return <span key={i} className={clase} style={estilo} />;
      })}
    </div>
  );
}

export function Barra({ avance = 0 }) {
  return (
    <div className="ct-barra">
      <div className="ct-barra__relleno" style={{ width: avance + "%" }} />
    </div>
  );
}

export function Chip({ children, oro = false }) {
  return <span className={"ct-chip" + (oro ? " ct-chip--oro" : "")}>{children}</span>;
}

export function Mensaje({ tipo = "cargando", children }) {
  return (
    <div
      className={"ct-mensaje ct-mensaje--" + tipo}
      role={tipo === "error" ? "alert" : "status"}
    >
      {children}
    </div>
  );
}

export function EncabezadoVista({ titulo, children }) {
  return (
    <section className="ct-encabezado-vista">
      <h1>{titulo}</h1>
      {children ? <p>{children}</p> : null}
    </section>
  );
}
