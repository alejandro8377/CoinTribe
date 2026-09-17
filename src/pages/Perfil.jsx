import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Topbar, Avatar, RielMonedas, Barra, Chip } from "../components/Interfaz";
import { usuario, courses } from "../data/content";
import { formatearCOP, porcentaje } from "../utils/formato";
import "../styles/profile.css";

/* Cuenta desde cero hasta el valor final; respeta "reducir movimiento". */
function useContador(valorFinal, duracion = 900) {
  const [valor, setValor] = useState(0);

  useEffect(() => {
    const quieto = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    if (quieto || !valorFinal) {
      setValor(valorFinal);
      return;
    }

    let animacion;
    const inicio = Date.now();

    const paso = () => {
      const avance = Math.min((Date.now() - inicio) / duracion, 1);
      const suave = 1 - Math.pow(1 - avance, 3);
      setValor(Math.round(valorFinal * suave));
      if (avance < 1) animacion = requestAnimationFrame(paso);
    };

    paso();
    return () => cancelAnimationFrame(animacion);
  }, [valorFinal, duracion]);

  return valor;
}

export default function Perfil() {
  const navegar = useNavigate();
  const avance = porcentaje(usuario.ahorro, usuario.meta);

  const ahorro = useContador(usuario.ahorro);
  const lecciones = useContador(usuario.lecciones);
  const retos = useContador(usuario.retosAceptados);

  const enMarcha = courses.filter((curso) => curso.progreso > 0 && curso.progreso < 100);

  return (
    <>
      <Topbar />

      <main className="ct-main">
        <section className="ct-encabezado-vista">
          <h1>Tu perfil</h1>
          <p>Todo lo que llevas acumulado en CoinTribe.</p>
        </section>

        <section className="ct-card perfil-cabecera">
          <Avatar tamano="lg" />
          <div className="perfil-cabecera__datos">
            <h2>{usuario.nombre}</h2>
            <p className="perfil-cabecera__email">{usuario.email}</p>
            <Chip oro>Nivel {usuario.nivel}</Chip>
          </div>
          <div className="perfil-cabecera__acciones">
            <Link to="/configuracion" className="ct-btn ct-btn--secundario">
              Editar datos
            </Link>
            <button
              type="button"
              className="ct-btn ct-btn--secundario"
              onClick={() => navegar("/login")}
            >
              Cerrar sesión
            </button>
          </div>
        </section>

        <section className="perfil-metricas" aria-label="Tus cifras">
          <article className="ct-card perfil-metrica">
            <p className="perfil-metrica__etiqueta">Ahorro acumulado</p>
            <p className="perfil-metrica__valor">{formatearCOP(ahorro)}</p>
          </article>

          <article className="ct-card perfil-metrica">
            <p className="perfil-metrica__etiqueta">Lecciones vistas</p>
            <p className="perfil-metrica__valor">{lecciones}</p>
          </article>

          <article className="ct-card perfil-metrica">
            <p className="perfil-metrica__etiqueta">Retos aceptados</p>
            <p className="perfil-metrica__valor">{retos}</p>
          </article>
        </section>

        <section className="ct-card">
          <h2>Avance de la meta del mes</h2>
          <div className="ct-progreso-info">
            <span>
              {formatearCOP(usuario.ahorro)} de {formatearCOP(usuario.meta)}
            </span>
            <strong>{avance}%</strong>
          </div>
          <RielMonedas avance={avance} />
        </section>

        <section className="ct-card">
          <h2>Cursos en marcha</h2>
          <p className="perfil-subtexto">Lo que has empezado y todavía no terminas.</p>

          <ul className="perfil-cursos">
            {enMarcha.length ? (
              enMarcha.map((curso) => (
                <li className="perfil-curso" key={curso.id}>
                  <div className="perfil-curso__cabeza">
                    <span>{curso.titulo}</span>
                    <span>{curso.progreso}%</span>
                  </div>
                  <Barra avance={curso.progreso} />
                </li>
              ))
            ) : (
              <li className="ct-vacio">
                Todavía no tienes cursos a medias. Empieza uno en la sección Cursos.
              </li>
            )}
          </ul>
        </section>
      </main>
    </>
  );
}
