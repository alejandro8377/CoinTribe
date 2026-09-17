import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { Topbar, RielMonedas, Barra, Chip } from "../components/Interfaz";
import { usuario, tips, courses } from "../data/content";
import { formatearCOP, saludo, porcentaje } from "../utils/formato";
import "../styles/home.css";

const ACCESOS = [
  {
    to: "/retos",
    icono: "🎯",
    titulo: "Retos de ahorro",
    texto: "Únete a un reto y suma bonos a tu progreso.",
  },
  {
    to: "/comunidad",
    icono: "💬",
    titulo: "Comunidad",
    texto: "Pregunta y comparte lo que te ha funcionado.",
  },
  {
    to: "/perfil",
    icono: "📈",
    titulo: "Tu progreso",
    texto: "Mira tus cursos, retos y ahorro acumulado.",
  },
];

export default function Inicio() {
  const [indiceTip, setIndiceTip] = useState(0);
  const avance = porcentaje(usuario.ahorro, usuario.meta);
  const tip = tips[indiceTip];
  const cursoPendiente =
    courses.find((curso) => curso.progreso > 0 && curso.progreso < 100) || courses[0];

  // El consejo rota solo cada nueve segundos.
  useEffect(() => {
    const temporizador = setInterval(() => {
      setIndiceTip((actual) => (actual + 1) % tips.length);
    }, 9000);
    return () => clearInterval(temporizador);
  }, []);

  function moverTip(paso) {
    setIndiceTip((actual) => (actual + paso + tips.length) % tips.length);
  }

  return (
    <>
      <Topbar />

      <main className="ct-main">
        <section className="ct-encabezado-vista">
          <h1>
            {saludo()}, {usuario.nombre.split(" ")[0]}
          </h1>
          <p>Este es el resumen de tu ahorro y de lo que dejaste a medias.</p>
        </section>

        <section className="ct-card home-ahorro" aria-labelledby="titulo-ahorro">
          <div className="home-ahorro__cifras">
            <div>
              <h2 id="titulo-ahorro">Ahorro del mes</h2>
              <p className="home-ahorro__monto">{formatearCOP(usuario.ahorro)}</p>
              <p className="home-ahorro__meta">Meta: {formatearCOP(usuario.meta)}</p>
            </div>
            <p className="home-ahorro__porcentaje">{avance}%</p>
          </div>

          <div className="ct-progreso-info">
            <span>Cada moneda equivale al 10% de tu meta</span>
            <strong>
              {formatearCOP(usuario.ahorro)} de {formatearCOP(usuario.meta)}
            </strong>
          </div>
          <RielMonedas avance={avance} />
        </section>

        <div className="home-columnas">
          <section className="ct-card home-tip">
            <div className="home-tip__cabeza">
              <span className="home-tip__icono" aria-hidden="true">
                {tip.icono}
              </span>
              <div>
                <Chip>{tip.categoria}</Chip>
                <h2>{tip.titulo}</h2>
              </div>
            </div>

            <p className="home-tip__texto">{tip.descripcion}</p>

            <div className="home-tip__controles">
              <button
                type="button"
                className="ct-btn ct-btn--secundario ct-btn--pequeno"
                onClick={() => moverTip(-1)}
              >
                Anterior
              </button>
              <button
                type="button"
                className="ct-btn ct-btn--secundario ct-btn--pequeno"
                onClick={() => moverTip(1)}
              >
                Siguiente
              </button>
            </div>
          </section>

          <section className="ct-card home-curso">
            <Chip oro>Lo dejaste a medias</Chip>
            <h2>{cursoPendiente.titulo}</h2>
            <p className="home-curso__texto">{cursoPendiente.descripcion}</p>
            <Barra avance={cursoPendiente.progreso} />
            <Link to="/cursos" className="ct-btn">
              Continuar el curso
            </Link>
          </section>
        </div>

        <section className="home-accesos" aria-label="Otras secciones">
          {ACCESOS.map((acceso) => (
            <Link key={acceso.to} to={acceso.to} className="home-acceso">
              <span className="home-acceso__icono" aria-hidden="true">
                {acceso.icono}
              </span>
              <h2>{acceso.titulo}</h2>
              <p>{acceso.texto}</p>
            </Link>
          ))}
        </section>
      </main>
    </>
  );
}
