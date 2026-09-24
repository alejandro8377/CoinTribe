/**
 * Capa de acceso a la API de CoinTribe (Spring Boot).
 * Base URL por defecto: http://localhost:8080
 * Puedes cambiarla con la variable de entorno VITE_API_URL.
 */

const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";
const CLIENT_NAME = "CoinTribe-Web";
const REQUEST_TIMEOUT_MS = 10000;

async function request(path, options = {}) {
  const url = `${BASE_URL}${path}`;
  const controller = new AbortController();
  const timeout = setTimeout(() => controller.abort(), REQUEST_TIMEOUT_MS);
  const config = {
    headers: {
      "Content-Type": "application/json",
      "X-CoinTribe-Client": CLIENT_NAME,
      ...(options.headers || {}),
    },
    signal: controller.signal,
    ...options,
  };

  try {
    const response = await fetch(url, config);
    const data = await response.json().catch(() => ({}));

    if (!response.ok) {
      const message = data.error || data.message || `Error HTTP ${response.status}`;
      throw new Error(message);
    }
    return data;
  } catch (error) {
    if (error.name === "AbortError") {
      throw new Error("CoinTribe no recibió respuesta del backend a tiempo");
    }
    throw error;
  } finally {
    clearTimeout(timeout);
  }
}

// ——— Usuarios ———
export const api = {
  register: (nombre, email, password) =>
    request("/api/users/register", {
      method: "POST",
      body: JSON.stringify({ nombre, email, password }),
    }),

  login: (email, password) =>
    request("/api/users/login", {
      method: "POST",
      body: JSON.stringify({ email, password }),
    }),

  getUser: (id) => request(`/api/users/${id}`),

  updateProfile: (id, { nombre, meta }) =>
    request(`/api/users/${id}/profile`, {
      method: "PUT",
      body: JSON.stringify({ nombre, meta }),
    }),

  changePassword: (id, currentPassword, newPassword) =>
    request(`/api/users/${id}/password`, {
      method: "PUT",
      body: JSON.stringify({ currentPassword, newPassword }),
    }),

  addAhorro: (id, monto) =>
    request(`/api/users/${id}/ahorro`, {
      method: "POST",
      body: JSON.stringify({ monto }),
    }),

  // ——— Cursos ———
  getCourses: () => request("/api/courses"),
  getCourse: (id) => request(`/api/courses/${id}`),
  updateCourseProgress: (id, progreso) =>
    request(`/api/courses/${id}/progress`, {
      method: "PUT",
      body: JSON.stringify({ progreso }),
    }),
  completeLesson: (userId, lecciones = 1) =>
    request("/api/courses/complete-lesson", {
      method: "POST",
      body: JSON.stringify({ userId, lecciones }),
    }),

  // ——— Retos ———
  getChallenges: () => request("/api/challenges"),
  joinChallenge: (userId, challengeId) =>
    request(`/api/challenges/${challengeId}/join`, {
      method: "POST",
      body: JSON.stringify({ userId }),
    }),
  depositChallenge: (userId, challengeId, monto) =>
    request(`/api/challenges/${challengeId}/deposit`, {
      method: "POST",
      body: JSON.stringify({ userId, monto }),
    }),
  getUserChallenges: (userId) => request(`/api/challenges/user/${userId}`),

  // ——— Comunidad ———
  getPosts: () => request("/api/posts"),
  createPost: (autor, etiqueta, titulo, cuerpo) =>
    request("/api/posts", {
      method: "POST",
      body: JSON.stringify({ autor, etiqueta, titulo, cuerpo }),
    }),
  likePost: (postId, userId) =>
    request(`/api/posts/${postId}/like`, {
      method: "POST",
      body: JSON.stringify({ userId }),
    }),

  // ——— Logros ———
  getAchievements: () => request("/api/achievements"),
  getUserAchievements: (userId) => request(`/api/achievements/user/${userId}`),

  // ——— Coins (ejemplo original) ———
  getCoins: () => request("/api/coins"),
};

export default api;
