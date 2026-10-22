# CoinTribe

API backend de **educación financiera personal** con gamificación y comunidad.

> **Sello CoinTribe:** aprende, ahorra y avanza en tribu. Cada pequeño hábito cuenta cuando lo conviertes en progreso.

Stack:
- **Backend:** Spring Boot (Java 21) + JPA + H2

Equipo actual: **3 integrantes**.

La experiencia combina aprendizaje breve, retos medibles y reconocimiento automático para que mejorar las finanzas se sienta como un progreso compartido.

**Mantenimiento:** sebastianCardona · `scardona12345678@cesde.net`

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

### Ejecutar el backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

API disponible en `http://localhost:8080`  
Consola H2: `http://localhost:8080/h2-console`  
(JDBC URL: `jdbc:h2:mem:cointribedb`, user: `sa`, password vacía)

---

## Usuario de prueba (seed)

| Campo      | Valor                |
|------------|----------------------|
| Email      | admin@cointribe.com  |
| Contraseña | 123456               |

---

<<<<<<< HEAD
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
=======
## Endpoints principales del backend (CRUD completo)

### Usuarios `/api/users`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET  | `/api/users` | Listar todos los usuarios |
| GET  | `/api/users/{id}` | Obtener usuario por ID |
| POST | `/api/users/register` | Registrar usuario |
| POST | `/api/users/login` | Iniciar sesión |
| PUT  | `/api/users/{id}` | Actualizar usuario (nombre, email, meta, nivel) |
| PUT  | `/api/users/{id}/profile` | Actualizar perfil (nombre, meta) |
| PUT  | `/api/users/{id}/password` | Cambiar contraseña |
| POST | `/api/users/{id}/ahorro` | Sumar ahorro |
| PUT  | `/api/users/{id}/deactivate` | Desactivar cuenta (soft-delete) |
| PUT  | `/api/users/{id}/activate` | Reactivar cuenta |
| DELETE | `/api/users/{id}` | Eliminar usuario (físico) |

### Cursos `/api/courses`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET  | `/api/courses` | Listar todos los cursos |
| GET  | `/api/courses/{id}` | Obtener curso por ID |
| GET  | `/api/courses/tema/{tema}` | Filtrar cursos por tema |
| POST | `/api/courses` | Crear curso |
| PUT  | `/api/courses/{id}` | Actualizar curso |
| PUT  | `/api/courses/{id}/progress` | Actualizar progreso (0-100, solo avanzar) |
| POST | `/api/courses/complete-lesson` | Marcar lecciones completadas |
| DELETE | `/api/courses/{id}` | Eliminar curso |

### Retos `/api/challenges`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET  | `/api/challenges` | Listar retos activos |
| GET  | `/api/challenges/all` | Listar todos los retos |
| GET  | `/api/challenges/{id}` | Obtener reto por ID |
| GET  | `/api/challenges/user/{userId}` | Retos de un usuario |
| POST | `/api/challenges` | Crear reto |
| PUT  | `/api/challenges/{id}` | Actualizar reto |
| POST | `/api/challenges/{id}/join` | Unirse a un reto |
| POST | `/api/challenges/{id}/deposit` | Registrar depósito en un reto |
| DELETE | `/api/challenges/{id}` | Eliminar reto |

### Publicaciones `/api/posts`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET  | `/api/posts` | Listar publicaciones |
| GET  | `/api/posts/{id}` | Obtener publicación por ID |
| POST | `/api/posts` | Crear publicación |
| PUT  | `/api/posts/{id}` | Actualizar publicación |
| POST | `/api/posts/{id}/like` | Dar like (una sola vez por usuario) |
| DELETE | `/api/posts/{id}` | Eliminar publicación |

### Logros `/api/achievements`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET  | `/api/achievements` | Listar logros |
| GET  | `/api/achievements/{id}` | Obtener logro por ID |
| GET  | `/api/achievements/user/{userId}` | Logros de un usuario |
| POST | `/api/achievements` | Crear logro |
| PUT  | `/api/achievements/{id}` | Actualizar logro |
| DELETE | `/api/achievements/{id}` | Eliminar logro |

### Coins `/api/coins`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET  | `/api/coins` | Listar monedas |
| GET  | `/api/coins/{id}` | Obtener moneda por ID |
| POST | `/api/coins` | Crear moneda |
| PUT  | `/api/coins/{id}` | Actualizar moneda |
| DELETE | `/api/coins/{id}` | Eliminar moneda |
>>>>>>> e5646a2 (Nuevos endpoints e implementacion de base de datos en NEON)

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
├── backend/                  # Spring Boot API
│   └── src/main/java/com/cointribe/backend/
│       ├── models/           # User, Course, Challenge, Post, Achievement, Coin…
│       ├── repositories/
│       ├── services/         # Reglas de negocio
│       ├── controllers/      # API REST
│       ├── config/           # DataInitializer (seed)
│       └── exceptions/       # BusinessException + handler global
├── .gitignore
└── README.md
```

---

## Notas

- Las contraseñas se guardan en texto plano solo con fines académicos. En producción deben hashearse (BCrypt).
- H2 es en memoria: los datos se reinician al apagar el backend.
