package com.dbd.arma;

/**
 * Representa un arma de tipo distancia.
 * <p>
 * Esta clase es una implementación concreta de {@link Arma}. 
 * Se caracteriza por tener estadísticas equilibradas, ideales para 
 * supervivientes o killers que buscan un balance entre daño y probabilidad de acierto.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * @version 1.0
 * @see Arma
 */


public class Gancho extends Arma {
    /**
     * Crea una nueva instancia de Gancho con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Gancho</li>
     * <li><b>Daño:</b> 25</li>
     * <li><b>Precisión:</b> 85%</li>
     * </ul>
     * </p>
     */
    public Gancho() {
        // Daño alto (25), precisión alta (85)
        super("Gancho Oxidado", 25, 85);
    }

    @Override
    public void usar() {
        
    }
}
