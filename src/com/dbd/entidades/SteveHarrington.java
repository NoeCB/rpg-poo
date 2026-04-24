package com.dbd.entidades;

/**
 * Representa a Steve Harrington, un superviviente del juego inspirado en el
 * personaje de Stranger Things.
 * <p>
 * Steve Harrington es un superviviente dedicado a proteger a otros, con el
 * instinto natural
 * de defender a sus compañeros del peligro. Su vida máxima de 110 puntos lo
 * posiciona como
 * el superviviente más resistente en términos de vida máxima, aunque con baja
 * defensa base.
 * </p>
 * Con una defensa base de 10, Steve Harrington mitiga solo 2 puntos de daño
 * (10/5) por ataque,
 * similar a Feng Min. Sin embargo, compensa con su vida máxima de 110 HP, la
 * más alta entre
 * los supervivientes, convirtiéndolo en un tanque de resistencia pura.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class SteveHarrington extends Personaje {
    /**
     * Constructor que inicializa la instancia de Steve Harrington con sus atributos
     * específicos.
     * <p>
     * Estadísticas de Steve Harrington:
     * <ul>
     * <li><strong>Nombre</strong>: "Steve Harrington" (el protector del grupo)</li>
     * <li><strong>Vida Actual</strong>: 110 HP (la más alta entre
     * supervivientes)</li>
     * <li><strong>Vida Máxima</strong>: 110 HP (clasificado como superviviente por
     * ser \u2264130)</li>
     * <li><strong>Defensa Base</strong>: 10 (mitiga 2 puntos de daño por ataque =
     * 10/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial
     * inicial)</li>
     * </ul>
     * Steve Harrington hereda todas las mecánicas de combate base de la clase
     * {@link Personaje},
     * incluyendo el sistema asimétrico donde supervivientes obtienen:
     * <ul>
     * <li><strong>Evasón mejorada</strong>: 20% (vs 5% de killers)</li>
     * <li><strong>Daño sin armas</strong>: 20-34 HP (vs 12-21 de killers)</li>
     * </ul>
     * Su estrategia es la vida máxima más alta, no la defensa, convirtiéndolo en un
     * tanque por pool de vida en lugar de mitigación defensiva.
     * </p>
     */
    public SteveHarrington() {
        super("Steve Harrington", 110, 110, 10);
    }

    /**
     * Ejecuta la acción específica de Steve Harrington cuando es su turno (método
     * abstracto implementado).
     * <p>
     * Steve Harrington se arregla el pelo y busca a alguien a quien proteger,
     * mostrando su
     * naturaleza altruista y dedicada a defender a sus compañeros. Este método es
     * principalmente
     * de flavour/narrativa y se ejecuta al inicio de su turno, reflejando su rol
     * protector en el grupo.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina
     * automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)} o
     * manualmente
     * en modo juego player-controlled.
     */

}