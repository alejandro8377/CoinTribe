import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Marca, Mensaje } from "../components/Interfaz";
import "../styles/auth.css";
import "../styles/create-account.css";

const CAMPOS_INICIALES = {
  nombre: "",
  correo: "",
  contrasena: "",
  confirmar: "",
  terminos: false,
};

export default function CrearCuenta() {
  const navegar = useNavigate();
  const [campos, setCampos] = useState(CAMPOS_INICIALES);
  const [errores, setErrores] = useState([]);
  const [exito, setExito] = useState(false);

  function cambiar(clave, valor) {
    setCampos((anterior) => ({ ...anterior, [clave]: valor }));
  }

  function validar() {
    const lista = [];
    if (!campos.nombre.trim()) lista.push("Escribe tu nombre completo.");
    if (!campos.correo.trim()) lista.push("Escribe tu correo electrónico.");
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(campos.correo))
      lista.push("El correo debe tener la forma nombre@dominio.com.");
    if (campos.contrasena.length < 6)
      lista.push("La contraseña necesita al menos 6 caracteres.");
    if (campos.contrasena !== campos.confirmar)
      lista.push("Las dos contraseñas deben ser iguales.");
    if (!campos.terminos) lista.push("Marca la casilla de términos para continuar.");
    return lista;
  }

  function manejarEnvio(evento) {
    evento.preventDefault();
    const lista = validar();
    setErrores(lista);

    if (lista.length) return;

    setExito(true);
    setTimeout(() => navegar("/login"), 1400);
  }

  return (
    <div className="acceso">
      <main className="acceso-tarjeta">
        <aside className="acceso-panel">
          <Marca />

          <p className="acceso-panel__frase">
            Crea tu cuenta y empieza con un reto de ahorro esta misma semana.
          </p>

          <ul className="acceso-panel__lista">
            <li>Tu progreso queda guardado en este navegador</li>
            <li>Puedes cambiar tus datos cuando quieras</li>
            <li>Sin costo y sin tarjeta</li>
          </ul>

          <p className="acceso-panel__demo">
            ¿Ya tienes cuenta?
            <br />
            <Link to="/login" className="acceso-panel__enlace">
              Iniciar sesión
            </Link>
          </p>
        </aside>

        <section className="acceso-form">
          <h1>Crear cuenta</h1>
          <p className="acceso-form__intro">Solo necesitas un correo y una contraseña.</p>

          <form onSubmit={manejarEnvio} noValidate>
            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Nombre completo</span>
              <input
                className="ct-input"
                type="text"
                autoComplete="name"
                value={campos.nombre}
                onChange={(e) => cambiar("nombre", e.target.value)}
              />
            </label>

            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Correo electrónico</span>
              <input
                className="ct-input"
                type="email"
                autoComplete="email"
                value={campos.correo}
                onChange={(e) => cambiar("correo", e.target.value)}
              />
            </label>

            <div className="registro-columnas">
              <label className="ct-campo">
                <span className="ct-campo__etiqueta">Contraseña</span>
                <input
                  className="ct-input"
                  type="password"
                  autoComplete="new-password"
                  value={campos.contrasena}
                  onChange={(e) => cambiar("contrasena", e.target.value)}
                />
                <span className="ct-ayuda">Mínimo 6 caracteres.</span>
              </label>

              <label className="ct-campo">
                <span className="ct-campo__etiqueta">Repite la contraseña</span>
                <input
                  className="ct-input"
                  type="password"
                  autoComplete="new-password"
                  value={campos.confirmar}
                  onChange={(e) => cambiar("confirmar", e.target.value)}
                />
              </label>
            </div>

            <label className="ct-check">
              <input
                type="checkbox"
                checked={campos.terminos}
                onChange={(e) => cambiar("terminos", e.target.checked)}
              />
              <span>Acepto los términos de uso y el tratamiento de mis datos.</span>
            </label>

            {errores.length ? (
              <div className="acceso-form__estado">
                <Mensaje tipo="error">
                  Revisa estos puntos antes de continuar:
                  <ul>
                    {errores.map((error) => (
                      <li key={error}>{error}</li>
                    ))}
                  </ul>
                </Mensaje>
              </div>
            ) : null}

            {exito ? (
              <div className="acceso-form__estado">
                <Mensaje tipo="exito">Cuenta creada. Te llevamos al inicio de sesión…</Mensaje>
              </div>
            ) : null}

            <button type="submit" className="ct-btn ct-btn--bloque" disabled={exito}>
              {exito ? "Cuenta creada" : "Crear cuenta"}
            </button>
          </form>

          <p className="acceso-form__pie">
            ¿Ya eres parte de la tribu? <Link to="/login">Iniciar sesión</Link>
          </p>
        </section>
      </main>
    </div>
  );
}
