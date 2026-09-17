/* CoinTribe · utilidades de presentación */

export function formatearCOP(valor) {
  const numero = Number(valor);
  if (!Number.isFinite(numero)) return "$0";
  return "$" + Math.round(numero).toLocaleString("es-CO");
}

export function iniciales(nombre) {
  const partes = String(nombre || "Invitado").trim().split(/\s+/);
  const primera = partes[0]?.charAt(0) || "I";
  const segunda = partes[1]?.charAt(0) || "";
  return (primera + segunda).toUpperCase();
}

export function saludo() {
  const hora = new Date().getHours();
  if (hora >= 5 && hora < 12) return "Buenos días";
  if (hora >= 12 && hora < 19) return "Buenas tardes";
  return "Buenas noches";
}

export function porcentaje(parte, total) {
  if (!total) return 0;
  return Math.min(Math.round((parte / total) * 100), 100);
}
