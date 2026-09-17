import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Topbar, Mensaje } from "../components/Interfaz";
import { usuario } from "../data/content";
import "../styles/settings.css";

const PREFERENCIAS = [
  {
    clave: "notificacionEmail",
    titulo: "Resumen por correo",
    texto: "Un correo semanal con tu avance de ahorro.",
  },
  {
    clave: "notificacionPush",
    titulo: "Recordatorios en el navegador",
    texto: "Avisos cuando un reto está por vencer.",
  },
  {
    clave: "perfilPublico",
    titulo: "Perfil visible para la tribu",
    texto: "Otras personas pueden ver tu nombre y tus logros.",
  },
  {
    clave: "mostrarActividad",
    titulo: "Mostrar mi actividad",
    texto: "Tus publicaciones y comentarios aparecen con tu nombre.",
  },
];

export default function Configuracion() {
  const navegar = useNavigate();

  const [perfil, setPerfil] = useState({
    nombre: usuario.nombre,
    email: usuario.email,
    meta: usuario.meta,
  });
  const [avisoPerfil, setAvisoPerfil] = useState(null);
  const [avisoPassword, setAvisoPassword] = useState(null);
  const [preferencias, setPreferencias] = useState({
    notificacionEmail: true,
    notificacionPush: true,
    perfilPublico: false,
    mostrarActividad: true,
  });

  function guardarPerfil(evento) {
    evento.preventDefault();
    const errores = [];

    if (!perfil.nombre.trim()) errores.push("Escribe tu nombre completo.");
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(perfil.email))
      errores.push("El correo debe tener la forma nombre@dominio.com.");
    if (!Number(perfil.meta) || Number(perfil.meta) <= 0)
      errores.push("La meta debe ser un número mayor que cero.");

    setAvisoPerfil(
      errores.length
        ? { tipo: "error", errores }
        : { tipo: "exito", texto: "Datos guardados." },
    );
  }

  function cambiarPassword(evento) {
    evento.preventDefault();
    const datos = new FormData(evento.target);
    const nueva = String(datos.get("nueva") || "");
    const confirmar = String(datos.get("confirmar") || "");
    const errores = [];

    if (!datos.get("actual")) errores.push("Escribe tu contraseña actual.");
    if (nueva.length < 6) errores.push("La nueva contraseña necesita al menos 6 caracteres.");
    if (nueva !== confirmar) errores.push("Las dos contraseñas nuevas deben ser iguales.");

    if (errores.length) {
      setAvisoPassword({ tipo: "error", errores });
      return;
    }

    evento.target.reset();
    setAvisoPassword({ tipo: "exito", texto: "Contraseña actualizada." });
  }

  function pintarAviso(aviso) {
    if (!aviso) return null;

    return (
      <Mensaje tipo={aviso.tipo}>
        {aviso.errores ? (
          <>
            Revisa estos puntos:
            <ul>
              {aviso.errores.map((error) => (
                <li key={error}>{error}</li>
              ))}
            </ul>
          </>
        ) : (
          aviso.texto
        )}
      </Mensaje>
    );
  }

  return (
    <>
      <Topbar />

      <main className="ct-main ajustes-layout">
        <section className="ct-encabezado-vista ajustes-intro">
          <h1>Configuración</h1>
          <p>Cambia tus datos, decide qué avisos quieres recibir y qué ve la comunidad.</p>
        </section>

        <section className="ct-card">
          <h2>Datos de la cuenta</h2>
          <form onSubmit={guardarPerfil} noValidate>
            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Nombre completo</span>
              <input
                className="ct-input"
                type="text"
                value={perfil.nombre}
                onChange={(e) => setPerfil({ ...perfil, nombre: e.target.value })}
              />
            </label>

            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Correo electrónico</span>
              <input
                className="ct-input"
                type="email"
                value={perfil.email}
                onChange={(e) => setPerfil({ ...perfil, email: e.target.value })}
              />
            </label>

            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Meta de ahorro mensual</span>
              <input
                className="ct-input"
                type="number"
                min="0"
                step="10000"
                value={perfil.meta}
                onChange={(e) => setPerfil({ ...perfil, meta: e.target.value })}
              />
              <span className="ct-ayuda">Se usa para calcular tu avance en Inicio y Retos.</span>
            </label>

            {pintarAviso(avisoPerfil)}
            <button type="submit" className="ct-btn">
              Guardar cambios
            </button>
          </form>
        </section>

        <section className="ct-card">
          <h2>Contraseña</h2>
          <form onSubmit={cambiarPassword} noValidate>
            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Contraseña actual</span>
              <input className="ct-input" type="password" name="actual" />
            </label>

            <div className="ajustes-columnas">
              <label className="ct-campo">
                <span className="ct-campo__etiqueta">Nueva contraseña</span>
                <input className="ct-input" type="password" name="nueva" />
              </label>

              <label className="ct-campo">
                <span className="ct-campo__etiqueta">Repite la nueva</span>
                <input className="ct-input" type="password" name="confirmar" />
              </label>
            </div>

            {pintarAviso(avisoPassword)}
            <button type="submit" className="ct-btn">
              Cambiar contraseña
            </button>
          </form>
        </section>

        <section className="ct-card">
          <h2>Avisos y privacidad</h2>
          <p className="ajustes-subtexto">Los cambios se guardan solos.</p>

          {PREFERENCIAS.map((preferencia) => (
            <div className="ct-switch" key={preferencia.clave}>
              <span className="ct-switch__texto">
                <strong>{preferencia.titulo}</strong>
                <span>{preferencia.texto}</span>
              </span>
              <span className="ct-switch__control">
                <input
                  type="checkbox"
                  checked={preferencias[preferencia.clave]}
                  aria-label={preferencia.titulo}
                  onChange={(e) =>
                    setPreferencias({
                      ...preferencias,
                      [preferencia.clave]: e.target.checked,
                    })
                  }
                />
                <span className="ct-switch__pista" />
              </span>
            </div>
          ))}
        </section>

        <section className="ct-card ajustes-zona-datos">
          <h2>Datos del prototipo</h2>
          <p className="ajustes-subtexto">
            Esta versión es solo la parte visual: los cambios viven mientras la página esté
            abierta y se pierden al recargar.
          </p>
          <div className="ajustes-acciones">
            <button
              type="button"
              className="ct-btn ct-btn--secundario"
              onClick={() => window.location.reload()}
            >
              Restablecer datos de ejemplo
            </button>
            <button
              type="button"
              className="ct-btn ct-btn--secundario"
              onClick={() => navegar("/login")}
            >
              Cerrar sesión
            </button>
          </div>
        </section>
      </main>
    </>
  );
}
