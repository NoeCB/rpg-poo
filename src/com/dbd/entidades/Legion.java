package com.dbd.entidades;

/**
 * Representa a Legion, un asesino (killer) del juego que representa un grupo de asesinos descontrolados.
 * <p>
 * Legion es un killer ágil y frenético que ataca sin clemencia. Su vida máxima de 180 puntos
 * lo posiciona como un asesino equilibrado, menos resistente que Onryo o Animatrónico pero aún
 * mucho más fuerte que cualquier superviviente individual.
 * </p>
 * Con una defensa base de 25, Legion puede mitigar un daño adicional de 5 puntos (25/5) en cada ataque,
 * proporcionando una buena resistencia defensiva a pesar de tener menos vida que otros killers.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Legion extends Personaje {
    /**
     * Constructor que inicializa la instancia de Legion con sus atributos específicos.
     * <p>
     * Estadísticas de Legion:
     * <ul>
     * <li><strong>Nombre</strong>: "Legion" (los asesinos del grupo)</li>
     * <li><strong>Vida Actual</strong>: 180 HP (resistencia moderada)</li>
     * <li><strong>Vida Máxima</strong>: 180 HP (clasificado como killer por ser > 130)</li>
     * <li><strong>Defensa Base</strong>: 25 (mitiga 5 puntos de daño por ataque = 25/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial inicial)</li>
     * </ul>
     * Legion hereda todas las mecánicas de combate base de la clase {@link Personaje},
     * incluyendo el sistema asimétrico de daño, evasión reducida (5%) y IA de combate automática.
     * A diferencia de otros killers, Legion tiene una defensa superior gracias a su valor de 25.
     * </p>
     */
    public Legion() {
        super("Legion", 180, 180, 25, 0);
    }

    /**
     * Ejecuta la acción específica de Legion cuando es su turno (método abstracto implementado).
     * <p>
     * Legion entra en frenésí y comienza a correr hacia el jugador, demostrando su naturaleza
     * agresiva y descontrolada. Este método es principalmente de flavour/narrativa y se
     * ejecuta al inicio de su turno para crear tensión y dinamismo en la batalla.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)}.
     */
    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " entra en frenesí y empieza a correr hacia ti.");
    }
}