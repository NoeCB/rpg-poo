package com.dbd.estados;

import com.dbd.core.Util;
import com.dbd.entidades.Personaje;

/**
 * Estado de Hemorragia que inflige daño de desangramiento al personaje afectado.
 * <p>
 * Cuando un personaje sufre una hemorragia, su cuerpo pierde sangre de forma continua
 * durante varios turnos. Este estado es similar a la maldición en que causa daño a lo largo
 * del tiempo, pero con menor intensidad (5 puntos por turno vs 10 de la maldición).
 * </p>
 * Cada turno que dura la hemorragia, el personaje pierde 5 puntos de vida de forma directa,
 * sin posibilidad de mitigación por defensas o armaduras.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Hemorragia extends Estado {
    /**
     * Constructor que inicializa el estado de Hemorragia.
     * <p>
     * El estado se crea con el nombre "Hemorragia" y una duración especificada.
     * </p>
     *
     * @param turnosRestantes Número de turnos que durará el efecto de sangrado
     */
    public Hemorragia(int turnosRestantes) {
        super("Hemorragia", turnosRestantes);
    }

    /**
     * Aplica el efecto de hemorragia al personaje objetivo.
     * <p>
     * El efecto:
     * <ol>
     * <li>Muestra un mensaje indicando que el personaje está sangrando profusamente.</li>
     * <li><strong>Inflige 5 puntos de daño directo</strong> por desangramiento, que no puede
     *     ser mitigado por defensas o armaduras.</li>
     * <li>Decrementa el contador de turnos restantes del estado.</li>
     * </ol>
     * A diferencia del sistema de daño normal en {@link Personaje#recibirDanio(int)},
     * este daño es <strong>puro y sin mitigaciones</strong>. Aunque moins potente que la
     * maldición (5 vs 10 daño por turno), es el estado de sangrado persistente ideal
     * para debilitar enemigos de forma gradual.
     * </p>
     *
     * @param objetivo El personaje que sufrirá los efectos del desangramiento
     */
    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // Daño persistente cada turno
        int danioHemorragia = 5;
        System.out.println(Util.ROJO + objetivo.getNombrePersonaje() + " sangra profusamente y pierde " + danioHemorragia + " de vida." + Util.RESET);
        
        objetivo.setVidaActual(objetivo.getVidaActual() - danioHemorragia);
        
        this.turnosRestantes--;
    }
}
