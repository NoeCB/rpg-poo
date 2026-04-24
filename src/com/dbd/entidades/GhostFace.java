
package com.dbd.entidades;

/**
 * Representa a Ghost Face, un asesino (killer) del juego inspirado en la
 * película "Scream".
 * <p>
 * Ghost Face es un asesino sigilo y estratega que acecha a sus presas sin ser
 * visto,
 * utilizando tácticas de asusto y persecución. Su vida máxima de 150 puntos lo
 * posiciona
 * como un killer de resistencia moderada, más equilibrado que otros asesinos
 * que alcanzan
 * los 180-220 HP.
 * </p>
 * Con una defensa base de 15, Ghost Face mitiga 3 puntos de daño (15/5) por
 * ataque,
 * proporcionando una defensa moderada. Su estrategia se basa más en sigilo y
 * movimiento
 * que en resistencia pura, diferenciándolo de asesinos más fuertes.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class GhostFace extends Personaje {
    /**
     * Constructor que inicializa la instancia de Ghost Face con sus atributos
     * específicos.
     * <p>
     * Estadísticas de Ghost Face:
     * <ul>
     * <li><strong>Nombre</strong>: "Ghost Face" (el acechador enmascarado)</li>
     * <li><strong>Vida Actual</strong>: 150 HP (resistencia moderada)</li>
     * <li><strong>Vida Máxima</strong>: 150 HP (clasificado como killer por ser >
     * 130)</li>
     * <li><strong>Defensa Base</strong>: 15 (mitiga 3 puntos de daño por ataque =
     * 15/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial
     * inicial)</li>
     * </ul>
     * Ghost Face hereda todas las mecánicas de combate base de la clase
     * {@link Personaje},
     * incluyendo el sistema asimétrico de daño, evasión reducida (5%) y IA de
     * combate automática.
     * A diferencia de killers más fuertes, Ghost Face es más equilibrado en
     * términos de vida.
     * </p>
     */
    public GhostFace() {
        super("Ghost Face", 150, 150, 15);
    }

    /**
     * Ejecuta la acción específica de Ghost Face cuando es su turno (método
     * abstracto implementado).
     * <p>
     * Ghost Face se asoma por una esquina acechando en silencio, mostrando su
     * naturaleza
     * sigilosa y estratégica. Este método es principalmente de flavour/narrativa y
     * se
     * ejecuta al inicio de su turno para crear tensión y misterio.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina
     * automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)}.
     */

}
