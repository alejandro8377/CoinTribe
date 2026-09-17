import { useMemo, useState } from "react";
import { Topbar, Barra, Chip } from "../components/Interfaz";
import { courses } from "../data/content";
import { porcentaje } from "../utils/formato";
import "../styles/courses.css";

export default function Cursos() {
  const [busqueda, setBusqueda] = useState("");
  const [tema, setTema] = useState("Todos");
  const [cursoId, setCursoId] = useState(courses[0].id);
  // La parte visual guarda las lecciones marcadas solo mientras dura la sesión.
  const [vistas, setVistas] = useState({});

  const temas = useMemo(
    () => ["Todos", ...new Set(courses.map((curso) => curso.tema).filter(Boolean))],
    [],
  );

  const visibles = useMemo(() => {
    const texto = busqueda.trim().toLowerCase();
    return courses.filter((curso) => {
      const coincideTexto =
        curso.titulo.toLowerCase().includes(texto) ||
        (curso.descripcion || "").toLowerCase().includes(texto);
      const coincideTema = tema === "Todos" || curso.tema === tema;
      return coincideTexto && coincideTema;
    });
  }, [busqueda, tema]);

  const curso = visibles.find((item) => item.id === cursoId) || visibles[0] || null;
  const marcadas = (curso && vistas[curso.id]) || [];
  const avance = curso ? porcentaje(marcadas.length, curso.lecciones.length) : 0;

  function alternarLeccion(leccionId) {
    setVistas((anterior) => {
      const actuales = anterior[curso.id] || [];
      const nuevas = actuales.includes(leccionId)
        ? actuales.filter((id) => id !== leccionId)
        : [...actuales, leccionId];
      return { ...anterior, [curso.id]: nuevas };
    });
  }

  function progresoVisible(item) {
    const marcadasDelCurso = vistas[item.id];
    if (marcadasDelCurso) return porcentaje(marcadasDelCurso.length, item.lecciones.length);
    return item.progreso || 0;
  }

  return (
    <>
      <Topbar />

      <main className="ct-main">
        <section className="ct-encabezado-vista">
          <h1>Cursos</h1>
          <p>Lecciones cortas. Marca cada una al terminarla para ver tu avance.</p>
        </section>

        <div className="cursos-layout">
          <aside className="ct-card cursos-lista" aria-label="Catálogo de cursos">
            <label className="ct-campo">
              <span className="ct-campo__etiqueta">Buscar</span>
              <input
                className="ct-input"
                type="search"
                placeholder="Presupuesto, deudas, inversión…"
                value={busqueda}
                onChange={(e) => setBusqueda(e.target.value)}
              />
            </label>

            <div className="cursos-filtros" role="group" aria-label="Filtrar por tema">
              {temas.map((item) => (
                <button
                  key={item}
                  type="button"
                  aria-pressed={item === tema}
                  onClick={() => setTema(item)}
                >
                  {item}
                </button>
              ))}
            </div>

            <ul className="cursos-lista__items">
              {visibles.map((item) => (
                <li key={item.id}>
                  <button
                    type="button"
                    className="curso-item"
                    aria-current={curso && item.id === curso.id}
                    onClick={() => setCursoId(item.id)}
                  >
                    <span className="curso-item__titulo">{item.titulo}</span>
                    <span className="curso-item__meta">
                      {item.lecciones.length} lecciones · {progresoVisible(item)}% visto
                    </span>
                  </button>
                </li>
              ))}
            </ul>

            {visibles.length === 0 ? (
              <p className="ct-vacio">
                Ningún curso coincide con esa búsqueda. Prueba con otra palabra.
              </p>
            ) : null}
          </aside>

          <section className="ct-card curso-detalle" aria-live="polite">
            {curso ? (
              <>
                <Chip>{curso.tema}</Chip>
                <h2>{curso.titulo}</h2>
                <p className="curso-detalle__descripcion">{curso.descripcion}</p>

                <div className="ct-progreso-info">
                  <span>
                    {marcadas.length} de {curso.lecciones.length} lecciones
                  </span>
                  <strong>{avance}%</strong>
                </div>
                <Barra avance={avance} />

                <ul className="lecciones">
                  {curso.lecciones.map((leccion) => {
                    const vista = marcadas.includes(leccion.id);
                    return (
                      <li
                        key={leccion.id}
                        className={"leccion" + (vista ? " leccion--vista" : "")}
                      >
                        <span className="leccion__icono" aria-hidden="true">
                          {leccion.icono}
                        </span>
                        <span className="leccion__texto">
                          <span className="leccion__titulo">{leccion.titulo}</span>
                          <span className="leccion__duracion">{leccion.duracion}</span>
                        </span>
                        <button
                          type="button"
                          className={
                            "ct-btn ct-btn--pequeno" + (vista ? " ct-btn--secundario" : "")
                          }
                          onClick={() => alternarLeccion(leccion.id)}
                        >
                          {vista ? "Marcar sin ver" : "Marcar como vista"}
                        </button>
                      </li>
                    );
                  })}
                </ul>

                {marcadas.length > 0 ? (
                  <div className="curso-detalle__acciones">
                    <button
                      type="button"
                      className="ct-btn ct-btn--secundario"
                      onClick={() =>
                        setVistas((anterior) => ({ ...anterior, [curso.id]: [] }))
                      }
                    >
                      Reiniciar progreso del curso
                    </button>
                  </div>
                ) : null}
              </>
            ) : (
              <>
                <h2>Selecciona un curso</h2>
                <p className="curso-detalle__descripcion">
                  Elige un curso de la lista para ver sus lecciones.
                </p>
              </>
            )}
          </section>
        </div>
      </main>
    </>
  );
}
