package com.dbd.estados;

import com.dbd.core.Util;
import com.dbd.entidades.Personaje;

/**
 * Estado de Vigor que proporciona regeneración y fortaleza al personaje afectado.
 * <p>
 * Cuando un personaje tiene vigor activo, experimenta una ola de energía que le permite
 * recuperar vida de forma continua durante varios turnos. Este es el único estado
 * beneficial en el juego, opuesto a los efectos negativos como ceguera o maldición.
 * </p>
 * Cada turno que dura el vigor, el personaje recupera 8 puntos de vida, hasta alcanzar
 * su máximo de salud. El efecto nunca permite superar la vida máxima.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Vigor extends Estado {
    /**
     * Constructor que inicializa el estado de Vigor.
     * <p>
     * El estado se crea con el nombre "Vigor" y una duración especificada.
     * </p>
     *
     * @param turnosRestantes Número de turnos que durará el efecto regenerativo de vigor
     */
    public Vigor(int turnosRestantes) {
        super("Vigor", turnosRestantes);
    }

    /**
     * Aplica el efecto de vigor al personaje objetivo.
     * <p>
     * El efecto:
     * <ol>
     * <li>Muestra un mensaje indicando que el personaje siente un subidón de vigor y energía.</li>
     * <li><strong>Restaura 8 puntos de vida</strong> al personaje afectado.</li>
     * <li>Garantiza que la vida nunca supere el máximo permitido (la ajusta si es necesario).</li>
     * <li>Decrementa el contador de turnos restantes del estado.</li>
     * </ol>
     * A diferencia de los estados negativos que debilitan (ceguera, maldición),
     * el vigor es el único estado beneficial que fortalece al personaje.
     * </p>
     *
     * @param objetivo El personaje que recibirá los efectos regenerativos del vigor
     */
    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // Cura un poco de vida cada turno
        int curacion = 8;
        System.out.println(Util.VERDE + objetivo.getNombrePersonaje() + " siente un subidón de Vigor y recupera " + curacion + " de vida." + Util.RESET);
        
        objetivo.setVidaActual(objetivo.getVidaActual() + curacion);
        
        if (objetivo.getVidaActual() > objetivo.getVidaMax()) {
            objetivo.setVidaActual(objetivo.getVidaMax());
        }
        
        this.turnosRestantes--;
    }
}
