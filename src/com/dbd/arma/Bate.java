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

public class Bate extends Arma {
    public Bate() {
        /**
         * Crea una nueva instancia de Bate con valores predeterminados.
         * <p>
         * Las estadísticas base para esta arma son:
         * <ul>
         * <li><b>Nombre:</b> Bate de Béisbol</li>
         * <li><b>Daño:</b> 15</li>
         * <li><b>Precisión:</b> 75%</li>
         * </ul>
         * </p>
         */

        super("Bate de Béisbol", 15, 75);
    }

}
