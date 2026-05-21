package com.dbd.entidades;

/**
 * Representa a Onryo, un asesino (killer) del juego inspirado en la película
 * "The Ring".
 * <p>
 * Onryo es un killer clásico de alta resistencia que emerge desde misterios
 * tecnológicos.
 * Su vida máxima de 220 puntos lo posiciona como uno de los asesinos más
 * resistentes del juego,
 * capaz de soportar múltiples ataques mientras persigue a los supervivientes.
 * </p>
 * Con una defensa base de 20, Onryo puede mitigar un daño adicional de 4 puntos
 * (20/5) en cada ataque.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Onryo extends Personaje {
    /**
     * Constructor que inicializa la instancia de Onryo con sus atributos
     * específicos.
     * <p>
     * Estadísticas de Onryo:
     * <ul>
     * <li><strong>Nombre</strong>: "Onryo" (el espíritu de La Maldición)</li>
     * <li><strong>Vida Actual</strong>: 220 HP (muy resistente)</li>
     * <li><strong>Vida Máxima</strong>: 220 HP (clasificado como killer por ser >
     * 130)</li>
     * <li><strong>Defensa Base</strong>: 20 (mitiga 4 puntos de daño por ataque =
     * 20/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial
     * inicial)</li>
     * </ul>
     * Onryo hereda todas las mecánicas de combate base de la clase
     * {@link Personaje},
     * incluyendo el sistema asimétrico de daño, evasión reducida (5%) y IA de
     * combate.
     * </p>
     */
    public Onryo() {
        super("Onryo", 220, 220, 20);
    }

    /**
     * Ejecuta la acción específica de Onryo cuando es su turno (método abstracto
     * implementado).
     * <p>
     * Onryo realiza un acto de aparición dramática, emergiendo desde un televisor
     * cercano,
     * basándose en sus orígenes la película "The Ring". Este método es
     * principalmente
     * de flavour/narrativa y se ejecuta al inicio de su turno.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina mediante
     * {@link Personaje#decidirAccionIA(ArrayList, ArrayList)} en modo automático.
     */

}