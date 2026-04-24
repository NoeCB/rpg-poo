package com.dbd.entidades;

/**
 * Representa a Leon Kennedy, un superviviente del juego inspirado en el policía
 * de Resident Evil.
 * <p>
 * Leon Kennedy es un superviviente entrenado y meticuloso que se prepara
 * estratégicamente
 * para enfrentar el peligro. Su vida máxima de 105 puntos lo posiciona como una
 * superviviente
 * de resistencia moderada, compensada con mayor evasón (20%) y daño superior en
 * grupo (20-34 HP).
 * </p>
 * Con una defensa base de 15, Leon Kennedy mitiga 3 puntos de daño (15/5) por
 * ataque,
 * proporcionándole una defensa moderada entre los supervivientes.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class LeonKennedy extends Personaje {
    /**
     * Constructor que inicializa la instancia de Leon Kennedy con sus atributos
     * específicos.
     * <p>
     * Estadísticas de Leon Kennedy:
     * <ul>
     * <li><strong>Nombre</strong>: "Leon Kennedy" (el policía estratégico)</li>
     * <li><strong>Vida Actual</strong>: 105 HP (resistencia moderada)</li>
     * <li><strong>Vida Máxima</strong>: 105 HP (clasificado como superviviente por
     * ser \u2264130)</li>
     * <li><strong>Defensa Base</strong>: 15 (mitiga 3 puntos de daño por ataque =
     * 15/5)</li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial
     * inicial)</li>
     * </ul>
     * Leon Kennedy hereda todas las mecánicas de combate base de la clase
     * {@link Personaje},
     * incluyendo el sistema asimétrico de daño donde supervivientes obtienen:
     * <ul>
     * <li><strong>Evasón mejorada</strong>: 20% (vs 5% de killers)</li>
     * <li><strong>Daño sin armas</strong>: 20-34 HP (vs 12-21 de killers)</li>
     * </ul>
     * </p>
     */
    public LeonKennedy() {
        super("Leon Kennedy", 105, 105, 15);
    }

    /**
     * Ejecuta la acción específica de Leon Kennedy cuando es su turno (método
     * abstracto implementado).
     * <p>
     * Leon Kennedy revisa su equipo cuidadosamente y se prepara para correr,
     * mostrando su
     * naturaleza estratégica y preparada. Este método es principalmente de
     * flavour/narrativa
     * y se ejecuta al inicio de su turno.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina
     * automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)} o
     * manualmente
     * en modo juego player-controlled.
     */

}