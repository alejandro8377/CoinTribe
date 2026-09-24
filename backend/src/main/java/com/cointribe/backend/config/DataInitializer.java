package com.cointribe.backend.config;

import com.cointribe.backend.models.*;
import com.cointribe.backend.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               CourseRepository courseRepository,
                               ChallengeRepository challengeRepository,
                               PostRepository postRepository,
                               AchievementRepository achievementRepository,
                               CoinRepository coinRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                return; // ya inicializado
            }

            // Usuario demo
            User demo = new User();
            demo.setNombre("Juan Pérez");
            demo.setEmail("admin@cointribe.com");
            demo.setPassword("123456");
            demo.setNivel(3);
            demo.setAhorro(320000.0);
            demo.setMeta(500000.0);
            demo.setLeccionesCompletadas(9);
            demo.setRetosAceptados(4);
            demo.setActivo(true);
            userRepository.save(demo);

            // Cursos
            Course c1 = new Course(null, "Presupuesto personal", "Bases",
                    "Organiza tu sueldo con el método 50/30/20 y deja de llegar justo a fin de mes.", 40, 4);
            Course c2 = new Course(null, "Fondo de emergencia", "Bases",
                    "Arma el colchón que te protege de un imprevisto sin recurrir a deudas.", 20, 4);
            Course c3 = new Course(null, "Deudas inteligentes", "Deudas",
                    "Aprende a priorizar y pagar deudas de forma estratégica.", 0, 3);
            Course c4 = new Course(null, "Primeros pasos en inversión", "Inversión",
                    "Fondos indexados y hábitos para invertir a largo plazo.", 10, 5);
            courseRepository.save(c1);
            courseRepository.save(c2);
            courseRepository.save(c3);
            courseRepository.save(c4);

            // Retos
            Challenge r1 = new Challenge(null, "Ahorra $50.000 esta semana",
                    "Guarda al menos $50.000 en 7 días evitando gastos hormiga.", 50000.0, 7, true);
            Challenge r2 = new Challenge(null, "Mes sin delivery",
                    "Cocina en casa durante 30 días y ahorra lo que gastarías en domicilio.", 150000.0, 30, true);
            Challenge r3 = new Challenge(null, "Fondo de emergencia inicial",
                    "Reúne el equivalente a un mes de gastos básicos.", 800000.0, 90, true);
            challengeRepository.save(r1);
            challengeRepository.save(r2);
            challengeRepository.save(r3);

            // Posts de comunidad
            Post p1 = new Post(null, "Marta Ríos", "Presupuesto",
                    "¿Cómo llevan el control de gastos hormiga?",
                    "Empecé a anotar cada café y snack. Me sorprendió cuánto se va. ¿Alguien usa alguna app recomendada?",
                    12, null);
            Post p2 = new Post(null, "Diego Herrera", "Ahorro",
                    "Completé mi primer reto de ahorro",
                    "Logré ahorrar $50.000 en una semana evitando el delivery. ¡Se puede!",
                    24, null);
            Post p3 = new Post(null, "Luisa Gómez", "Inversión",
                    "Consejos para empezar a invertir a largo plazo",
                    "Soy principiante y me gustaría invertir sin arriesgar de más. ¿Por dónde recomiendan empezar?",
                    9, null);
            postRepository.save(p1);
            postRepository.save(p2);
            postRepository.save(p3);

            // Logros
            Achievement a1 = new Achievement(null, "primer-deposito", "🪙", "Primer depósito",
                    "Registra tu primer depósito", 1.0, null, null);
            Achievement a2 = new Achievement(null, "mitad-meta", "🌱", "Vas por la mitad",
                    "Llega al 50% de tu meta del mes", 250000.0, null, null);
            Achievement a3 = new Achievement(null, "tres-retos", "🤝", "Tribu activa",
                    "Únete a tres retos", null, null, 3);
            Achievement a4 = new Achievement(null, "meta-cumplida", "🏆", "Meta del mes",
                    "Completa el 100% de tu meta", 500000.0, null, null);
            Achievement a5 = new Achievement(null, "estudiante", "📚", "Estudiante constante",
                    "Completa al menos 5 lecciones", null, 5, null);
            achievementRepository.save(a1);
            achievementRepository.save(a2);
            achievementRepository.save(a3);
            achievementRepository.save(a4);
            achievementRepository.save(a5);

            // Coins de ejemplo
            coinRepository.save(new Coin(null, "Bitcoin", "BTC", 65000.0));
            coinRepository.save(new Coin(null, "Ethereum", "ETH", 3500.0));
        };
    }
}
