package com.dbd.arma;

/**
 * Representa un arma de tipo melee.
 * <p>
 * Esta clase es una implementación concreta de {@link Arma}. 
 * Se caracteriza por realizar un cameo al juego ya que los supervivientes usan las linternas para cegar al killer.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * @version 1.0
 * @see Arma
 */


public class Motosierra extends Arma {
    /**
     * Crea una nueva instancia de Motosierra con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Motosierra</li>
     * <li><b>Daño:</b> 40</li>
     * <li><b>Precisión:</b> 50%</li>
     * </ul>
     * </p>
     */
    public Motosierra() {
       
        super("Motosierra", 40, 50);
    }

    @Override
    public void usar() {
      
    }
}
