package com.dbd.arma;

/**
 * Representa un arma de tipo distancia.
 * <p>
 * Esta clase es una implementación concreta de {@link Arma}. 
 * Se caracteriza por realizar un cameo al juego ya que los supervivientes usan las linternas para cegar al killer.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * @version 1.0
 * @see Arma
 */


public class Linterna extends Arma {
    /**
     * Crea una nueva instancia de Gancho con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Gancho</li>
     * <li><b>Daño:</b> 5</li>
     * <li><b>Precisión:</b> 90%</li>
     * </ul>
     * </p>
     */
    public Linterna() {
        // Daño muy bajo (5), mucha precisión (90)
        super("Linterna", 5, 90);
    }

    @Override
    public void usar() {
      
    }
}
