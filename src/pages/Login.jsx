import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Marca, Mensaje } from "../components/Interfaz";
import "../styles/auth.css";
import "../styles/login.css";

export default function Login() {
  const navegar = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [aviso, setAviso] = useState(null);

  // Versión visual: no valida contra ningún servicio, solo simula el flujo.
  function manejarEnvio(evento) {
    evento.preventDefault();

    if (!email || !password) {
      setAviso({ tipo: "error", texto: "Escribe tu correo y tu contraseña." });
      return;
    }

    setAviso({ tipo: "exito", texto: "Listo. Abriendo tu panel…" });
    setTimeout(() => navegar("/inicio"), 700);
  }

  return (
    <div className="acceso">
      <main className="acceso-tarjeta">
        <aside className="acceso-panel">
          <Marca />

          <p className="acceso-panel__frase">
            Tus metas de ahorro, tus cursos y tu tribu en un mismo lugar.
          </p>

          <ul className="acceso-panel__lista">
            <li>Cursos cortos de finanzas personales</li>
            <li>Retos de ahorro con logros</li>
            <li>Comunidad para resolver dudas</li>
          </ul>

          <p className="acceso-panel__demo">
            Cuenta de prueba
            <br />
            <strong>admin@cointribe.com</strong> · <strong>cointribe123</strong>
          </p>
        </aside>

        <section className="acceso-form">
          <h1>Iniciar sesión</h1>
          <p className="acceso-form__intro">Entra para seguir tu progreso.</p>

          <form onSubmit={manejarEnvio} noValidate>
            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Correo electrónico</span>
              <input
                className="ct-input"
                type="email"
                autoComplete="email"
                placeholder="tucorreo@ejemplo.com"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </label>

            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Contraseña</span>
              <input
                className="ct-input"
                type="password"
                autoComplete="current-password"
                placeholder="Mínimo 6 caracteres"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </label>

            {aviso ? (
              <div className="acceso-form__estado">
                <Mensaje tipo={aviso.tipo}>{aviso.texto}</Mensaje>
              </div>
            ) : null}

            <button type="submit" className="ct-btn ct-btn--bloque">
              Iniciar sesión
            </button>
          </form>

          <Link to="/inicio" className="ct-btn ct-btn--secundario ct-btn--bloque">
            Entrar como invitado
          </Link>

          <p className="acceso-form__pie">
            ¿Todavía no tienes cuenta? <Link to="/crear-cuenta">Crear una cuenta</Link>
          </p>
        </section>
      </main>
    </div>
  );
}
