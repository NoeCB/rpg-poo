package com.dbd.entidades;

/**
 * Representa al Animatrónico, un asesino (killer) del juego inspirado en "Five Nights at Freddy's".
 * <p>
 * El Animatrónico es un killer mecanizado de alta resistencia que acecha a los supervivientes
 * con movimientos y sonidos aterradores. Su vida máxima de 220 puntos lo posiciona como uno de
 * los asesinos más resistentes del juego, capaz de perseguir sostenidamente a sus presas.
 * </p>
 * Con una defensa base de 20, el Animatrónico puede mitigar un daño adicional de 4 puntos
 * (20/5) en cada ataque, proporcionando una resistencia defensiva sólida.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Animatronico extends Personaje {
    /**
     * Constructor que inicializa la instancia del Animatrónico con sus atributos específicos.
     * <p>
     * Estadísticas del Animatrónico:
     * <ul>
     * <li><strong>Nombre</strong>: "Animatrónico" (el robot terrorífico)</li>
     * <li><strong>Vida Actual</strong>: 220 HP (muy resistente)</li>
     * <li><strong>Vida Máxima</strong>: 220 HP (clasificado como killer por ser > 130)</li>
     * <li><strong>Defensa Base</strong>: 20 (mitiga 4 puntos de daño por ataque = 20/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial inicial)</li>
     * </ul>
     * El Animatrónico hereda todas las mecánicas de combate base de la clase {@link Personaje},
     * incluyendo el sistema asimétrico de daño, evasión reducida (5%) y IA de combate automática.
     * </p>
     */
    public Animatronico() {
        super("Animatrónico", 220, 220, 20, 0);
    }

    /**
     * Ejecuta la acción específica del Animatrónico cuando es su turno (método abstracto implementado).
     * <p>
     * El Animatrónico emite un sonido mecánico aterrador, basándose en sus orígenes de
     * "Five Nights at Freddy's". Este método es principalmente de flavour/narrativa y se
     * ejecuta al inicio de su turno para crear una atmósfera aterradora.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)}.
     */
    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " emite un sonido mecánico aterrador.");
    }
}
