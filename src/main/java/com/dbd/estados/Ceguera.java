package com.dbd.estados;

import com.dbd.entidades.Personaje;
import com.dbd.core.Util;

/**
 * Estado de Ceguera que desorienta al personaje afectado.
 * <p>
 * Cuando un personaje sufre ceguera, su capacidad de defensa se ve comprometida
 * durante varios turnos. Cada turno que dura el efecto, el personaje pierde
 * 2 puntos de defensa base, lo que lo hace más vulnerable a los ataques enemigos.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Ceguera extends Estado {
    /**
     * Constructor que inicializa el estado de Ceguera.
     * <p>
     * El estado se crea con el nombre "Ceguera" y una duración especificada.
     * </p>
     *
     * @param turnosRestantes Número de turnos que durará el efecto de ceguera
     */
    public Ceguera(int turnosRestantes) {
        super("Ceguera", turnosRestantes);
    }

    /**
     * Aplica el efecto de ceguera al personaje objetivo.
     * <p>
     * El efecto:
     * <ol>
     * <li>Muestra un mensaje indicando que el personaje está desorientado.</li>
     * <li>Reduce la defensa base del objetivo en 2 puntos, si tiene defensa disponible.</li>
     * <li>Decrementa el contador de turnos restantes del estado.</li>
     * </ol>
     * La reducción de defensa hace que el personaje sea más vulnerable a futuros ataques,
     * ya que su armadura y mitigación de daño se ven comprometidas.
     * </p>
     *
     * @param objetivo El personaje que sufrirá los efectos de la ceguera
     */
    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // Desorienta al objetivo quitándole un poco de defensa si es posible
        System.out.println(Util.CYAN + objetivo.getNombrePersonaje() + " sufre Ceguera y se desorienta en el combate." + Util.RESET);
        
        if (objetivo.getDefensaBase() > 0) {
            objetivo.setDefensaBase(objetivo.getDefensaBase() - 2);
        }
        
        this.turnosRestantes--;
    }
}
