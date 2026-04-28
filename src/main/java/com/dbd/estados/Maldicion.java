package com.dbd.estados;

import com.dbd.entidades.Personaje;

/**
 * Estado de Maldición que inflige daño directo al personaje afectado.
 * <p>
 * Cuando un personaje sufre una maldición, recibe daño continuo cada turno,
 * independientemente de su defensa o armadura. Este estado es especialmente peligroso
 * porque el daño no puede ser mitigado por ningún sistema defensivo.
 * </p>
 * Cada turno que dura la maldición, el personaje pierde 10 puntos de vida de forma directa.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Maldicion extends Estado {
    /**
     * Constructor que inicializa el estado de Maldición.
     * <p>
     * El estado se crea con el nombre "Maldición" y una duración especificada.
     * </p>
     *
     * @param turnos Número de turnos que durará el efecto de maldición
     */
    public Maldicion(int turnos) {
        super("Maldición", turnos);
    }

    /**
     * Aplica el efecto de maldición al personaje objetivo.
     * <p>
     * El efecto:
     * <ol>
     * <li>Muestra un mensaje indicando que el personaje está sufriendo los efectos de la maldición.</li>
     * <li><strong>Inflige 10 puntos de daño directo</strong> que no puede ser bloqueado
     *     ni mitigado por defensas o armaduras.</li>
     * <li>Decrementa el contador de turnos restantes del estado.</li>
     * </ol>
     * A diferencia del sistema de daño normal en {@link Personaje#recibirDanio(int)},
     * este daño es <strong>puro y sin mitigaciones</strong>, lo que hace la maldición
     * especialmente peligrosa cuando está activa.
     * </p>
     *
     * @param objetivo El personaje que sufrirá los efectos de la maldición
     */
    @Override
    public void aplicarEfecto(Personaje objetivo) {
        System.out.println(objetivo.getNombrePersonaje() + " sufre 10 de daño por la Maldición.");
        objetivo.setVidaActual(objetivo.getVidaActual() - 10);
        this.turnosRestantes--;
    }
}