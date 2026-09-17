import { useState } from "react";
import { Topbar, Avatar, Chip, Mensaje } from "../components/Interfaz";
import { posts, usuario } from "../data/content";
import "../styles/community.css";

const TEMAS = ["Ahorro", "Presupuesto", "Deudas", "Inversión"];

function Publicacion({ post, onMeGusta, onComentar }) {
  const [respuesta, setRespuesta] = useState("");

  function enviar(evento) {
    evento.preventDefault();
    if (!respuesta.trim()) return;
    onComentar(post.id, respuesta.trim());
    setRespuesta("");
  }

  return (
    <article className="publicacion">
      <div className="publicacion__cabeza">
        <Avatar nombre={post.autor} />
        <div>
          <p className="publicacion__autor">{post.autor}</p>
          <p className="publicacion__tiempo">{post.tiempo}</p>
        </div>
        <Chip>{post.etiqueta}</Chip>
      </div>

      <h2 className="publicacion__titulo">{post.titulo}</h2>
      <p className="publicacion__cuerpo">{post.cuerpo}</p>

      <div className="publicacion__acciones">
        <button
          type="button"
          className="publicacion__accion"
          aria-pressed={Boolean(post.meGusta)}
          onClick={() => onMeGusta(post.id)}
        >
          Me gusta · {post.likes}
        </button>
        <span className="publicacion__accion">Comentar · {post.comentarios.length}</span>
      </div>

      <div className="comentarios">
        {post.comentarios.map((comentario, indice) => (
          <div className="comentario" key={indice}>
            <Avatar nombre={comentario.autor} tamano="sm" />
            <div className="comentario__contenido">
              <span className="comentario__autor">{comentario.autor}</span>
              {comentario.texto}
            </div>
          </div>
        ))}

        <form className="comentario-form" onSubmit={enviar}>
          <input
            className="ct-input"
            type="text"
            placeholder="Escribe una respuesta"
            aria-label="Escribe una respuesta"
            value={respuesta}
            onChange={(e) => setRespuesta(e.target.value)}
          />
          <button type="submit" className="ct-btn ct-btn--pequeno">
            Enviar
          </button>
        </form>
      </div>
    </article>
  );
}

export default function Comunidad() {
  const [publicaciones, setPublicaciones] = useState(posts);
  const [titulo, setTitulo] = useState("");
  const [cuerpo, setCuerpo] = useState("");
  const [etiqueta, setEtiqueta] = useState(TEMAS[0]);
  const [aviso, setAviso] = useState(null);

  const conteoPorTema = publicaciones.reduce((acumulado, post) => {
    acumulado[post.etiqueta] = (acumulado[post.etiqueta] || 0) + 1;
    return acumulado;
  }, {});

  function alternarMeGusta(id) {
    setPublicaciones((lista) =>
      lista.map((post) =>
        post.id === id
          ? { ...post, meGusta: !post.meGusta, likes: post.likes + (post.meGusta ? -1 : 1) }
          : post,
      ),
    );
  }

  function agregarComentario(id, texto) {
    setPublicaciones((lista) =>
      lista.map((post) =>
        post.id === id
          ? { ...post, comentarios: [...post.comentarios, { autor: usuario.nombre, texto }] }
          : post,
      ),
    );
  }

  function publicar(evento) {
    evento.preventDefault();

    if (titulo.trim().length < 5 || cuerpo.trim().length < 10) {
      setAviso({
        tipo: "error",
        texto: "El título necesita al menos 5 caracteres y el mensaje 10.",
      });
      return;
    }

    setPublicaciones((lista) => [
      {
        id: "propia-" + Date.now(),
        autor: usuario.nombre,
        etiqueta,
        titulo: titulo.trim(),
        cuerpo: cuerpo.trim(),
        tiempo: "Hace un momento",
        likes: 0,
        comentarios: [],
      },
      ...lista,
    ]);

    setTitulo("");
    setCuerpo("");
    setAviso({ tipo: "exito", texto: "Publicado. Ya aparece en el feed." });
  }

  return (
    <>
      <Topbar />

      <main className="ct-main comunidad-layout">
        <div>
          <section className="ct-encabezado-vista">
            <h1>Comunidad</h1>
            <p>Preguntas reales sobre plata, respondidas por gente que ya pasó por ahí.</p>
          </section>

          <section className="ct-card comunidad-publicar">
            <h2>Escribe algo para la tribu</h2>
            <form onSubmit={publicar}>
              <label className="ct-campo">
                <span className="ct-campo__etiqueta">Título</span>
                <input
                  className="ct-input"
                  type="text"
                  maxLength={90}
                  value={titulo}
                  onChange={(e) => setTitulo(e.target.value)}
                />
              </label>

              <label className="ct-campo">
                <span className="ct-campo__etiqueta">Tu mensaje</span>
                <textarea
                  className="ct-input"
                  rows={3}
                  maxLength={500}
                  value={cuerpo}
                  onChange={(e) => setCuerpo(e.target.value)}
                />
              </label>

              <div className="comunidad-publicar__pie">
                <label className="ct-campo comunidad-publicar__tema">
                  <span className="ct-campo__etiqueta">Tema</span>
                  <select
                    className="ct-select"
                    value={etiqueta}
                    onChange={(e) => setEtiqueta(e.target.value)}
                  >
                    {TEMAS.map((tema) => (
                      <option key={tema}>{tema}</option>
                    ))}
                  </select>
                </label>
                <button type="submit" className="ct-btn">
                  Publicar
                </button>
              </div>

              {aviso ? (
                <div id="estado-publicacion">
                  <Mensaje tipo={aviso.tipo}>{aviso.texto}</Mensaje>
                </div>
              ) : null}
            </form>
          </section>

          <section className="comunidad-feed" aria-label="Publicaciones">
            {publicaciones.map((post) => (
              <Publicacion
                key={post.id}
                post={post}
                onMeGusta={alternarMeGusta}
                onComentar={agregarComentario}
              />
            ))}
          </section>
        </div>

        <aside className="comunidad-lateral">
          <section className="ct-card">
            <h2>Cómo funciona</h2>
            <ul className="comunidad-reglas">
              <li>Escribe con nombres y cifras concretas: ayuda más que los consejos generales.</li>
              <li>Nadie pide ni comparte datos bancarios aquí.</li>
              <li>Si algo te sirvió, dale un me gusta para que otros lo vean.</li>
            </ul>
          </section>

          <section className="ct-card">
            <h2>Temas activos</h2>
            <div className="comunidad-temas">
              {Object.entries(conteoPorTema).map(([tema, total]) => (
                <Chip key={tema}>
                  {tema} ({total})
                </Chip>
              ))}
            </div>
          </section>
        </aside>
      </main>
    </>
  );
}
