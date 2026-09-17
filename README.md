# CoinTribe — versión visual en React

Adaptación de CoinTribe a **React 18 + Vite + React Router**. Es la *parte
visual*: las mismas nueve pantallas, con la misma identidad, pero sin backend,
sin capa de datos y sin persistencia. El contenido sale de constantes en
`src/data/content.js` y las interacciones (buscar, filtrar, marcar lecciones,
dar me gusta, comentar) se manejan con estado local de React, así que se
reinician al recargar.

## Cómo ejecutarlo

Necesitas Node.js 18 o superior.

```bash
npm install
npm run dev
```

Vite abre `http://localhost:5173`. Para generar la versión de producción:

```bash
npm run build
npm run preview
```

## Rutas

| Ruta | Pantalla |
|------|----------|
| `/` | Portada del proyecto |
| `/login` | Iniciar sesión |
| `/crear-cuenta` | Crear cuenta |
| `/inicio` | Panel con el resumen de ahorro |
| `/cursos` | Catálogo y lecciones |
| `/retos` | Retos, depósitos y logros |
| `/comunidad` | Feed de publicaciones |
| `/perfil` | Estadísticas del usuario |
| `/configuracion` | Cuenta, contraseña y preferencias |

Cualquier ruta desconocida redirige a la portada.

## Estructura

```
cointribe-react/
├── index.html                  # Plantilla de Vite (fuentes y favicon)
├── package.json
├── vite.config.js
└── src/
    ├── main.jsx                # Punto de entrada y definición de rutas
    ├── components/
    │   └── Interfaz.jsx        # Marca, Topbar, Avatar, RielMonedas, Barra, Chip, Mensaje
    ├── pages/                  # Una pantalla por archivo
    ├── data/content.js         # Datos de ejemplo
    ├── utils/formato.js        # Moneda, iniciales, saludo y porcentajes
    └── styles/                 # Los mismos CSS del proyecto original
```

`base.css` se importa una sola vez en `main.jsx`; cada pantalla importa además su
propia hoja. Las clases (`ct-btn`, `ct-card`, `ct-monedas`…) son las mismas que
en la versión con HTML puro, así que el diseño no cambió.

## Qué cambió frente a la versión original

- La navegación entre archivos `.html` pasó a rutas de **React Router**
  (`<Link>` y `<NavLink>`); el enlace activo de la barra se marca solo.
- La capa `api.js` y las utilidades de sesión ya no existen: los datos son
  constantes importadas.
- Lo que antes se manipulaba con `document.getElementById` ahora es estado:
  `useState` para búsquedas, filtros, formularios y me gusta; `useEffect` para la
  rotación de consejos y para los contadores animados del perfil.
- Piezas repetidas (barra superior, avatar, riel de monedas, chips y mensajes)
  se volvieron componentes reutilizables en `components/Interfaz.jsx`.

## Si quieres volver a conectar datos

Reemplaza los `import` de `src/data/content.js` por llamadas con `fetch` dentro
de un `useEffect` (o un hook propio como `useRecurso`). La estructura de los
objetos es idéntica a la de los JSON del proyecto original, así que las
pantallas no necesitan cambios.
