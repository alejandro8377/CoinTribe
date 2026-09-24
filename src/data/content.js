/* CoinTribe · datos de ejemplo (frontend).
   El backend Spring Boot ya expone API real en /api/*.
   Estas constantes sirven de fallback visual y para la portada. */

export const students = [
  {
    "id": "1",
    "nombre": "Integrante 1",
    "rol": "Acceso, autenticación y perfil",
    "descripcion": "Login, registro con validaciones, perfil del usuario y configuración de cuenta.",
    "pantalla1": "Iniciar sesión / Crear cuenta",
    "ruta1": "/login",
    "pantalla2": "Perfil y Configuración",
    "ruta2": "/perfil"
  },
  {
    "id": "2",
    "nombre": "Integrante 2",
    "rol": "Panel, cursos y aprendizaje",
    "descripcion": "Resumen de ahorro, consejos rotativos, catálogo de cursos con buscador y progreso de lecciones.",
    "pantalla1": "Inicio",
    "ruta1": "/inicio",
    "pantalla2": "Cursos",
    "ruta2": "/cursos"
  },
  {
    "id": "3",
    "nombre": "Integrante 3",
    "rol": "Retos, comunidad y gamificación",
    "descripcion": "Retos de ahorro, depósitos, logros desbloqueables y foro de publicaciones con likes y comentarios.",
    "pantalla1": "Retos",
    "ruta1": "/retos",
    "pantalla2": "Comunidad",
    "ruta2": "/comunidad"
  }
];

export const courses = [
  {
    "id": "1",
    "titulo": "Presupuesto personal",
    "tema": "Bases",
    "descripcion": "Organiza tu sueldo con el método 50/30/20 y deja de llegar justo a fin de mes.",
    "progreso": 40,
    "lecciones": [
      {
        "id": "1-1",
        "titulo": "Registra tus ingresos reales",
        "icono": "🧾",
        "duracion": "6 min"
      },
      {
        "id": "1-2",
        "titulo": "Separa gastos fijos y variables",
        "icono": "📂",
        "duracion": "8 min"
      },
      {
        "id": "1-3",
        "titulo": "Aplica la regla 50/30/20",
        "icono": "⚖️",
        "duracion": "10 min"
      },
      {
        "id": "1-4",
        "titulo": "Revisa tu presupuesto cada semana",
        "icono": "🗓️",
        "duracion": "5 min"
      }
    ]
  },
  {
    "id": "2",
    "titulo": "Fondo de emergencia",
    "tema": "Bases",
    "descripcion": "Arma el colchón que te protege de un imprevisto sin recurrir a deudas.",
    "progreso": 20,
    "lecciones": [
      {
        "id": "2-1",
        "titulo": "Calcula tus gastos básicos de un mes",
        "icono": "🧮",
        "duracion": "7 min"
      },
      {
        "id": "2-2",
        "titulo": "Define cuántos meses guardar",
        "icono": "🛟",
        "duracion": "6 min"
      },
      {
        "id": "2-3",
        "titulo": "Elige dónde guardarlo",
        "icono": "🏦",
        "duracion": "9 min"
      }
    ]
  },
  {
    "id": "3",
    "titulo": "Salir de deudas",
    "tema": "Deuda",
    "descripcion": "Compara el método bola de nieve con el de avalancha y elige el que te sirve.",
    "progreso": 0,
    "lecciones": [
      {
        "id": "3-1",
        "titulo": "Lista todas tus deudas",
        "icono": "📋",
        "duracion": "8 min"
      },
      {
        "id": "3-2",
        "titulo": "Método bola de nieve",
        "icono": "❄️",
        "duracion": "11 min"
      },
      {
        "id": "3-3",
        "titulo": "Método avalancha",
        "icono": "🏔️",
        "duracion": "11 min"
      },
      {
        "id": "3-4",
        "titulo": "Negocia una tasa mejor",
        "icono": "🤝",
        "duracion": "9 min"
      }
    ]
  },
  {
    "id": "4",
    "titulo": "Primeros pasos para invertir",
    "tema": "Inversión",
    "descripcion": "Qué mirar antes de invertir tu primer peso y cómo evitar los errores típicos.",
    "progreso": 0,
    "lecciones": [
      {
        "id": "4-1",
        "titulo": "Riesgo, plazo y liquidez",
        "icono": "⏳",
        "duracion": "12 min"
      },
      {
        "id": "4-2",
        "titulo": "Diversificar en la práctica",
        "icono": "🧺",
        "duracion": "10 min"
      },
      {
        "id": "4-3",
        "titulo": "Fondos indexados explicados",
        "icono": "📈",
        "duracion": "13 min"
      }
    ]
  },
  {
    "id": "5",
    "titulo": "Compras inteligentes",
    "tema": "Hábitos",
    "descripcion": "Domina los gastos hormiga y aprende a comparar antes de pagar.",
    "progreso": 60,
    "lecciones": [
      {
        "id": "5-1",
        "titulo": "Detecta tus gastos hormiga",
        "icono": "🐜",
        "duracion": "6 min"
      },
      {
        "id": "5-2",
        "titulo": "La regla de las 24 horas",
        "icono": "⏱️",
        "duracion": "5 min"
      },
      {
        "id": "5-3",
        "titulo": "Compara precio por unidad",
        "icono": "🔍",
        "duracion": "7 min"
      }
    ]
  },
  {
    "id": "6",
    "titulo": "Crédito sin sustos",
    "tema": "Deuda",
    "descripcion": "Entiende tasas, cuotas y puntaje crediticio antes de firmar cualquier cosa.",
    "progreso": 0,
    "lecciones": [
      {
        "id": "6-1",
        "titulo": "Cómo se calcula una cuota",
        "icono": "🧾",
        "duracion": "9 min"
      },
      {
        "id": "6-2",
        "titulo": "Tasa efectiva vs nominal",
        "icono": "📊",
        "duracion": "10 min"
      },
      {
        "id": "6-3",
        "titulo": "Cuida tu historial crediticio",
        "icono": "🛡️",
        "duracion": "8 min"
      }
    ]
  }
];

export const tips = [
  {
    "id": "1",
    "icono": "🛟",
    "categoria": "Emergencias",
    "titulo": "Primero el colchón, después la inversión",
    "descripcion": "Guarda entre 3 y 6 meses de tus gastos básicos en una cuenta a la que puedas llegar rápido."
  },
  {
    "id": "2",
    "icono": "📊",
    "categoria": "Presupuesto",
    "titulo": "Revisa tus gastos variables",
    "descripcion": "Saber en qué se te va el dinero te deja ajustar hábitos sin renunciar a todo lo que disfrutas."
  },
  {
    "id": "3",
    "icono": "🎯",
    "categoria": "Metas",
    "titulo": "Ponle monto y fecha a tu meta",
    "descripcion": "Una meta con cifra y plazo es mucho más fácil de seguir que la intención general de ahorrar."
  },
  {
    "id": "4",
    "icono": "🐜",
    "categoria": "Hábitos",
    "titulo": "Los gastos hormiga suman",
    "descripcion": "Anota una semana completa de compras pequeñas y verás de dónde sale tu próximo ahorro."
  },
  {
    "id": "5",
    "icono": "🤖",
    "categoria": "Automatiza",
    "titulo": "Ahorra el mismo día que te pagan",
    "descripcion": "Programa el traslado a tu cuenta de ahorro apenas entra el sueldo, antes de empezar a gastar."
  }
];

export const challenges = [
  {
    "id": "r-1",
    "icono": "🏆",
    "titulo": "Ahorra $50.000 esta semana",
    "nivel": "Intermedio",
    "duracion": 7,
    "meta": 50000,
    "bono": 5000,
    "vence": "Vence el domingo",
    "destacado": true
  },
  {
    "id": "r-2",
    "icono": "🏦",
    "titulo": "Ahorra $100.000 en dos semanas",
    "nivel": "Intermedio",
    "duracion": 14,
    "meta": 100000,
    "bono": 8000
  },
  {
    "id": "r-3",
    "icono": "📊",
    "titulo": "Arma tu presupuesto semanal",
    "nivel": "Básico",
    "duracion": 7,
    "meta": 0,
    "bono": 4000
  },
  {
    "id": "r-4",
    "icono": "🚫",
    "titulo": "Una semana sin gastos hormiga",
    "nivel": "Básico",
    "duracion": 7,
    "meta": 0,
    "bono": 5000
  },
  {
    "id": "r-5",
    "icono": "🍱",
    "titulo": "Lleva almuerzo de casa 5 días",
    "nivel": "Básico",
    "duracion": 5,
    "meta": 0,
    "bono": 3000
  },
  {
    "id": "r-6",
    "icono": "💳",
    "titulo": "Abona extra a una de tus deudas",
    "nivel": "Intermedio",
    "duracion": 10,
    "meta": 0,
    "bono": 7000
  }
];

export const posts = [
  {
    "id": "p-1",
    "autor": "Marta Ríos",
    "etiqueta": "Ahorro",
    "titulo": "¿Cómo armar un fondo de emergencia desde cero?",
    "cuerpo": "Acabo de empezar a ahorrar y quiero tener un colchón para imprevistos. ¿Cuántos meses de gastos me recomiendan guardar y en qué tipo de cuenta conviene tenerlo?",
    "tiempo": "Hace 2 horas",
    "likes": 12,
    "comentarios": [
      {
        "autor": "Diego Herrera",
        "texto": "Lo ideal es entre 3 y 6 meses de tus gastos básicos. Yo lo tengo en una cuenta aparte para no gastarlo sin querer."
      },
      {
        "autor": "Luisa Gómez",
        "texto": "Empieza con una meta pequeña, por ejemplo un mes de gastos, y ve subiendo. Lo que cuenta es la constancia."
      }
    ]
  },
  {
    "id": "p-2",
    "autor": "Diego Herrera",
    "etiqueta": "Deudas",
    "titulo": "Por fin terminé de pagar todas mis deudas 🎉",
    "cuerpo": "Después de 14 meses con el método bola de nieve liquidé la última tarjeta. La tranquilidad de no deber nada no tiene precio. Ánimo a quienes van en el proceso.",
    "tiempo": "Hace 5 horas",
    "likes": 24,
    "comentarios": [
      {
        "autor": "Marta Ríos",
        "texto": "Felicitaciones Diego, eres toda una inspiración."
      },
      {
        "autor": "Andrés Pardo",
        "texto": "¿La bola de nieve te funcionó mejor que pagar primero la deuda de mayor interés?"
      },
      {
        "autor": "Luisa Gómez",
        "texto": "Me motivas un montón, voy por la mitad de la mía."
      }
    ]
  },
  {
    "id": "p-3",
    "autor": "Luisa Gómez",
    "etiqueta": "Inversión",
    "titulo": "Consejos para empezar a invertir a largo plazo",
    "cuerpo": "Soy principiante y me gustaría invertir sin arriesgar de más. ¿Por dónde recomiendan empezar y qué errores debería evitar el primer año?",
    "tiempo": "Hace 8 horas",
    "likes": 9,
    "comentarios": [
      {
        "autor": "Andrés Pardo",
        "texto": "Antes de invertir, asegúrate de tener listo tu fondo de emergencia. Después empieza con montos pequeños."
      },
      {
        "autor": "Diego Herrera",
        "texto": "Los fondos indexados son una buena puerta de entrada. Evita mover el dinero por pánico."
      }
    ]
  },
  {
    "id": "p-4",
    "autor": "Andrés Pardo",
    "etiqueta": "Presupuesto",
    "titulo": "Mi experiencia con el método 50/30/20",
    "cuerpo": "Llevo tres meses repartiendo el sueldo en 50% necesidades, 30% gustos y 20% ahorro. Me ordenó las finanzas por completo. ¿Alguien más lo usa?",
    "tiempo": "Hace 1 día",
    "likes": 15,
    "comentarios": [
      {
        "autor": "Marta Ríos",
        "texto": "Yo uso algo parecido pero subí el ahorro al 25%. Funciona muy bien con disciplina."
      },
      {
        "autor": "Luisa Gómez",
        "texto": "Gracias por compartir, voy a intentarlo este mes."
      }
    ]
  }
];

export const usuario = {
  "nombre": "Juan Pérez",
  "email": "admin@cointribe.com",
  "nivel": 3,
  "ahorro": 320000,
  "meta": 500000,
  "lecciones": 9,
  "retosAceptados": 4
};

export const logros = [
  {
    "id": "primer-deposito",
    "icono": "🪙",
    "nombre": "Primer depósito",
    "pista": "Registra tu primer depósito",
    "abierto": true
  },
  {
    "id": "mitad-meta",
    "icono": "🌱",
    "nombre": "Vas por la mitad",
    "pista": "Llega al 50% de tu meta del mes",
    "abierto": true
  },
  {
    "id": "tres-retos",
    "icono": "🤝",
    "nombre": "Tribu activa",
    "pista": "Únete a tres retos",
    "abierto": true
  },
  {
    "id": "meta-cumplida",
    "icono": "🏆",
    "nombre": "Meta del mes",
    "pista": "Completa el 100% de tu meta",
    "abierto": false
  }
];
