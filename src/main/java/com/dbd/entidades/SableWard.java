package com.dbd.entidades;

/**
 * Representa a Sable Ward, una superviviente defensiva del juego especializada
 * en protección.
 * <p>
 * Sable Ward es una superviviente defensiva y mistica que utiliza cánticos
 * protectores para
 * aumentar su resiliencia. Su vida máxima de 110 puntos la posiciona como una
 * superviviente
 * con resistencia media, pero lo que la distingue es su <strong>defensa base
 * excepcionalmente alta de 30</strong>,
 * la más alta entre todos los supervivientes (triple la de Feng Min).
 * </p>
 * Con una defensa base de 30, Sable Ward mitiga 6 puntos de daño (30/5) por
 * ataque,
 * convirtiéndola en la superviviente más defensiva del juego. Aunque más
 * vulnerable que los killers,
 * su alta defensa la hace ideal para roles de tanque defensivo en equipo.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class SableWard extends Personaje {
    /**
     * Constructor que inicializa la instancia de Sable Ward con sus atributos
     * específicos.
     * <p>
     * Estadísticas de Sable Ward:
     * <ul>
     * <li><strong>Nombre</strong>: "Sable Ward" (la guardiana protectora)</li>
     * <li><strong>Vida Actual</strong>: 110 HP (resistencia media)</li>
     * <li><strong>Vida Máxima</strong>: 110 HP (clasificada como superviviente por
     * ser \u2264130)</li>
     * <li><strong>Defensa Base</strong>: 30 (mitiga 6 puntos de daño por ataque =
     * 30/5) - <strong>LA MÁS ALTA</strong></li>
     * <li><strong>Puntos de Sangre</strong>: 0 (sin puntuación especial
     * inicial)</li>
     * </ul>
     * Sable Ward hereda todas las mecánicas de combate base de la clase
     * {@link Personaje},
     * incluyendo el sistema asimétrico donde obtiene:
     * <ul>
     * <li><strong>Evasón mejorada</strong>: 20% (vs 5% de killers)</li>
     * <li><strong>Daño sin armas</strong>: 20-34 HP (vs 12-21 de killers)</li>
     * </ul>
     * Su defensa de 30 es <strong>triple la de Feng Min (10)</strong>, haciéndola
     * la superviviente
     * más defensiva para protección de equipo.
     * </p>
     */
    public SableWard() {
        // Nombre, vidaActual, vidaMax, defensaBase, puntosSangre
        super("Sable Ward", 110, 110, 30);
    }

    /**
     * Ejecuta la acción específica de Sable Ward cuando es su turno (método
     * abstracto implementado).
     * <p>
     * Sable Ward murmura un cántico protector, mostrando su naturaleza mística y
     * defensiva.
     * Este método es principalmente de flavour/narrativa y se ejecuta al inicio de
     * su turno
     * para reforzar su identidad como guardiana protectora.
     * </p>
     * La acción real de combate (atacar, usar Perk, defender) se determina
     * automáticamente
     * mediante {@link Personaje#decidirAccionIA(ArrayList, ArrayList)} o
     * manualmente
     * en modo juego player-controlled.
     */

}
