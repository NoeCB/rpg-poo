package com.dbd.arma;

/**
 * Representa un arma de tipo melee.
 * <p>
 * Esta clase es una implementación concreta de {@link Arma}.
 * Se caracteriza por tener estadísticas equilibradas, ideales para
 * supervivientes o killers que buscan un balance entre daño y probabilidad de
 * acierto.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * 
 * @version 1.0
 * @see Arma
 */

public class Hacha extends Arma {
    /**
     * Crea una nueva instancia de Gancho con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Gancho</li>
     * <li><b>Daño:</b> 10</li>
     * <li><b>Precisión:</b> 70%</li>
     * </ul>
     * </p>
     */
    public Hacha() {
        super("Hacha", 10, 70);
    }

}
