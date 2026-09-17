import { Link } from "react-router-dom";
import { Marca, Avatar } from "../components/Interfaz";
import { students } from "../data/content";
import "../styles/landing.css";

export default function Portada() {
  return (
    <div className="portada">
      <header className="portada-barra">
        <Marca />
        <Link to="/login" className="ct-btn ct-btn--pequeno">
          Entrar a la app
        </Link>
      </header>

      <main>
        <section className="portada-hero">
          <p className="portada-hero__contexto">Proyecto integrador · Aplicación web</p>
          <h1 className="portada-hero__titulo">
            Aprender de plata
            <br />
            se lleva mejor en tribu
          </h1>
          <p className="portada-hero__texto">
            CoinTribe reúne cursos cortos, retos de ahorro y una comunidad para que
            cualquier persona ordene sus finanzas sin sentir que estudia contabilidad.
            Esta versión está construida con <code>React</code> y <code>Vite</code>.
          </p>

          <div className="portada-hero__acciones">
            <Link to="/login" className="ct-btn">
              Iniciar sesión
            </Link>
            <Link to="/crear-cuenta" className="ct-btn ct-btn--secundario">
              Crear una cuenta
            </Link>
          </div>

          <dl className="portada-demo">
            <div>
              <dt>Correo de prueba</dt>
              <dd>admin@cointribe.com</dd>
            </div>
            <div>
              <dt>Contraseña</dt>
              <dd>cointribe123</dd>
            </div>
          </dl>
        </section>

        <section className="portada-equipo" aria-labelledby="titulo-equipo">
          <div className="portada-equipo__encabezado">
            <h2 id="titulo-equipo">Quién hizo cada pantalla</h2>
            <p>Cuatro integrantes, dos vistas cada uno.</p>
          </div>

          <div className="portada-equipo__grid">
            {students.map((integrante) => (
              <article className="tarjeta-integrante" key={integrante.id}>
                <div className="tarjeta-integrante__cabeza">
                  <Avatar nombre={integrante.nombre} />
                  <div>
                    <h3>{integrante.nombre}</h3>
                    <p className="tarjeta-integrante__rol">{integrante.rol}</p>
                  </div>
                </div>

                <p className="tarjeta-integrante__descripcion">{integrante.descripcion}</p>

                <div className="tarjeta-integrante__pantallas">
                  <Link className="ct-btn ct-btn--pequeno" to={integrante.ruta1}>
                    {integrante.pantalla1}
                  </Link>
                  <Link
                    className="ct-btn ct-btn--pequeno ct-btn--secundario"
                    to={integrante.ruta2}
                  >
                    {integrante.pantalla2}
                  </Link>
                </div>
              </article>
            ))}
          </div>
        </section>
      </main>

      <footer className="ct-pie">
        <p>CoinTribe · versión visual en React, sin backend.</p>
      </footer>
    </div>
  );
}
