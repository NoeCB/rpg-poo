package com.dbd.estados;

import com.dbd.entidades.Personaje;

/**
 * Estado de Regeneración que proporciona curación potente al personaje afectado.
 * <p>
 * Cuando un personaje tiene regeneración activa, experimenta una curación acelerada
 * que le permite recuperar vida de forma rápida durante varios turnos. Este estado
 * es aún más potente que el vigor, restaurando más vida por turno.
 * </p>
 * Cada turno que dura la regeneración, el personaje recupera 15 puntos de vida.
 * La regeneración es especialmente efectiva para mantener a personajes en combates
 * prolongados o como contramedida contra enemigos que infligen mucho daño.
 * </p>
 *
 * @author Noelia Cantador y Luis Lázaro
 * @version 1.0
 */
public class Regeneracion extends Estado {
    /**
     * Constructor que inicializa el estado de Regeneración.
     * <p>
     * El estado se crea con el nombre "Regeneración" y una duración especificada.
     * </p>
     *
     * @param turnos Número de turnos que durará el efecto curativo de regeneración
     */
    public Regeneracion(int turnos) {
        super("Regeneración", turnos);
    }

    /**
     * Aplica el efecto de regeneración al personaje objetivo.
     * <p>
     * El efecto:
     * <ol>
     * <li>Muestra un mensaje indicando que el personaje recupera vida por regeneración.</li>
     * <li><strong>Restaura 15 puntos de vida</strong> al personaje afectado (más potente que Vigor).</li>
     * <li>Decrementa el contador de turnos restantes del estado.</li>
     * </ol>
     * A diferencia del estado de Vigor que recupera 8 puntos por turno, la regeneración
     * es un efecto más potente que permite curación acelerada en combates críticos.
     * </p>
     *
     * @param objetivo El personaje que recibirá los efectos regenerativos
     */
    @Override
    public void aplicarEfecto(Personaje objetivo) {
        System.out.println(objetivo.getNombrePersonaje() + " recupera 15 de vida por Regeneración.");
        objetivo.setVidaActual(objetivo.getVidaActual() + 15);
        this.turnosRestantes--;
    }
}