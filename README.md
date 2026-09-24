# CoinTribe

Aplicación de **educación financiera personal** con gamificación y comunidad.

> **Sello CoinTribe:** aprende, ahorra y avanza en tribu. Cada pequeño hábito cuenta cuando lo conviertes en progreso.

Stack:
- **Frontend:** React 18 + Vite + React Router
- **Backend:** Spring Boot (Java 21) + JPA + H2

Equipo actual: **3 integrantes**.

La experiencia combina aprendizaje breve, retos medibles y reconocimiento automático para que mejorar las finanzas se sienta como un progreso compartido.

---

## Idea del proyecto

CoinTribe ayuda a las personas a mejorar sus hábitos de dinero mediante:

- Cursos cortos de finanzas personales
- Retos de ahorro con metas y logros
- Panel de progreso de ahorro
- Comunidad (foro) para compartir dudas y experiencias
- Perfil con estadísticas y logros desbloqueables

---

## Cómo ejecutar

### Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

API disponible en `http://localhost:8080`  
Consola H2: `http://localhost:8080/h2-console`  
(JDBC URL: `jdbc:h2:mem:cointribedb`, user: `sa`, password vacía)

### Frontend (React)

```bash
npm install
npm run dev
```

Abre `http://localhost:5173`.

La capa de API del frontend apunta por defecto a `http://localhost:8080`.  
Puedes cambiarla con la variable de entorno `VITE_API_URL`.

---

## Usuario de prueba (seed)

| Campo      | Valor                |
|------------|----------------------|
| Email      | admin@cointribe.com  |
| Contraseña | 123456               |

---

## Endpoints principales del backend

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/users/register` | Registrar usuario |
| POST | `/api/users/login` | Iniciar sesión |
| GET  | `/api/users/{id}` | Obtener usuario |
| PUT  | `/api/users/{id}/profile` | Actualizar perfil |
| PUT  | `/api/users/{id}/password` | Cambiar contraseña |
| POST | `/api/users/{id}/ahorro` | Sumar ahorro |
| GET  | `/api/courses` | Listar cursos |
| PUT  | `/api/courses/{id}/progress` | Actualizar progreso (0-100) |
| POST | `/api/courses/complete-lesson` | Marcar lecciones completadas |
| GET  | `/api/challenges` | Listar retos activos |
| POST | `/api/challenges/{id}/join` | Unirse a un reto |
| POST | `/api/challenges/{id}/deposit` | Registrar depósito en un reto |
| GET  | `/api/posts` | Listar publicaciones |
| POST | `/api/posts` | Crear publicación |
| POST | `/api/posts/{id}/like` | Dar like (una sola vez por usuario) |
| GET  | `/api/achievements` | Listar logros |
| GET  | `/api/achievements/user/{userId}` | Logros de un usuario |
| GET  | `/api/coins` | Coins de ejemplo (CRUD original) |

---

## 6 reglas de negocio implementadas en Services

1. **Email único al registrar** (`UserService.registerUser`)  
   No se permite crear dos cuentas con el mismo correo.

2. **Contraseña mínima y formato de email** (`UserService.registerUser` / `changePassword`)  
   Contraseña ≥ 6 caracteres; email con formato válido; la nueva contraseña debe ser distinta de la actual.

3. **No unirse dos veces al mismo reto** (`ChallengeService.joinChallenge`)  
   Un usuario solo puede tener una relación activa con cada reto.

4. **Depósito válido en retos** (`ChallengeService.registerDeposit`)  
   El monto debe ser > 0 y no puede hacer que el ahorro del reto supere su meta.

5. **Progreso de curso entre 0 y 100** (`CourseService.updateProgress`)  
   No se aceptan valores fuera de rango ni se permite retroceder el progreso.

6. **Un solo like por usuario y publicación** (`PostService.likePost`)  
   Intentos posteriores de like del mismo usuario sobre el mismo post se rechazan.

Además, el `AchievementService` evalúa automáticamente condiciones (ahorro, lecciones, retos) y desbloquea logros.

---

## Estructura del proyecto

```
CoinTribe/
├── backend/                  # Spring Boot
│   └── src/main/java/com/cointribe/backend/
│       ├── models/           # User, Course, Challenge, Post, Achievement, Coin…
│       ├── repositories/
│       ├── services/         # Reglas de negocio
│       ├── controllers/      # API REST
│       ├── config/           # DataInitializer (seed)
│       └── exceptions/       # BusinessException + handler global
├── src/                      # React
│   ├── pages/
│   ├── components/
│   ├── data/content.js
│   ├── services/api.js       # Cliente HTTP hacia el backend
│   ├── styles/
│   └── utils/
├── package.json
└── README.md
```

---

## Notas

- El frontend sigue usando datos mock en varias pantallas; la capa `src/services/api.js` ya está lista para conectar Login, Registro, Retos, Comunidad, etc.
- Las contraseñas se guardan en texto plano solo con fines académicos. En producción deben hashearse (BCrypt).
- H2 es en memoria: los datos se reinician al apagar el backend.
