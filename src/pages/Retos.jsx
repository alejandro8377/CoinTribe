import { useState } from "react";
import { Topbar, RielMonedas, Chip } from "../components/Interfaz";
import { challenges, logros, usuario } from "../data/content";
import { formatearCOP, porcentaje } from "../utils/formato";
import "../styles/challenges.css";

const DEPOSITO = 5000;

export default function Retos() {
  const [ahorro, setAhorro] = useState(usuario.ahorro);
  const [unidos, setUnidos] = useState([]);

  const destacado = challenges.find((reto) => reto.destacado) || challenges[0];
  const disponibles = challenges.filter((reto) => reto !== destacado);
  const avance = porcentaje(ahorro, usuario.meta);
  const completo = ahorro >= usuario.meta;

  function unirse(reto) {
    setUnidos((anterior) => [...anterior, reto.id]);
    setAhorro((anterior) => Math.min(anterior + reto.bono, usuario.meta));
  }

  return (
    <>
      <Topbar />

      <main className="ct-main">
        <section className="ct-encabezado-vista">
          <h1>Retos de ahorro</h1>
          <p>Un reto a la vez. Cada depósito que registras suma a tu ahorro del mes.</p>
        </section>

        <section className="ct-card reto-activo">
          <div className="reto-activo__cabeza">
            <span className="reto-activo__icono" aria-hidden="true">
              {destacado.icono}
            </span>
            <div>
              <Chip>Reto en curso</Chip>
              <h2 className="reto-activo__titulo">{destacado.titulo}</h2>
              <p className="reto-activo__vence">{destacado.vence}</p>
            </div>
          </div>

          <div className="ct-progreso-info">
            <span>Ahorro del mes</span>
            <strong>
              {formatearCOP(ahorro)} de {formatearCOP(usuario.meta)}
            </strong>
          </div>
          <RielMonedas avance={avance} />

          <div className="reto-activo__acciones">
            <button
              type="button"
              className="ct-btn ct-btn--oro"
              disabled={completo}
              onClick={() => setAhorro((anterior) => Math.min(anterior + DEPOSITO, usuario.meta))}
            >
              {completo
                ? "Meta cumplida"
                : "Registrar depósito de " + formatearCOP(DEPOSITO)}
            </button>
            <span className="reto-activo__nota">{avance}% de tu meta mensual</span>
          </div>
        </section>

        <section className="ct-card">
          <h2>Tus logros</h2>
          <p className="retos-subtexto">Se desbloquean solos a medida que avanzas.</p>

          <div className="logros">
            {logros.map((logro) => {
              const abierto =
                logro.id === "meta-cumplida" ? completo : logro.abierto || unidos.length >= 3;

              return (
                <article
                  key={logro.id}
                  className={"logro" + (abierto ? " logro--abierto" : "")}
                >
                  <div className="logro__medalla" aria-hidden="true">
                    {logro.icono}
                  </div>
                  <p className="logro__nombre">{logro.nombre}</p>
                  <p className="logro__pista">{abierto ? "Desbloqueado" : logro.pista}</p>
                </article>
              );
            })}
          </div>
        </section>

        <section className="ct-card">
          <h2>Retos disponibles</h2>
          <p className="retos-subtexto">Al unirte sumamos el bono del reto a tu ahorro.</p>

          <div className="retos-disponibles">
            {disponibles.map((reto) => {
              const yaUnido = unidos.includes(reto.id);

              return (
                <article
                  key={reto.id}
                  className={"reto-fila" + (yaUnido ? " reto-fila--unido" : "")}
                >
                  <span className="reto-fila__icono" aria-hidden="true">
                    {reto.icono}
                  </span>
                  <div>
                    <p className="reto-fila__titulo">{reto.titulo}</p>
                    <p className="reto-fila__meta">
                      Nivel {reto.nivel.toLowerCase()}, {reto.duracion} días, bono de{" "}
                      {formatearCOP(reto.bono)}
                    </p>
                  </div>
                  <button
                    type="button"
                    className={"ct-btn ct-btn--pequeno" + (yaUnido ? " ct-btn--secundario" : "")}
                    disabled={yaUnido}
                    onClick={() => unirse(reto)}
                  >
                    {yaUnido ? "Ya estás dentro" : "Unirme"}
                  </button>
                </article>
              );
            })}
          </div>
        </section>
      </main>
    </>
  );
}
