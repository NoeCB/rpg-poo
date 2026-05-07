package com.dbd.arma;

/**
 * Representa un arma a distancia.
 * <p>
 * La {@code Pistola} se distingue por su alta fiabilidad (precisión)
 * a cambio de un daño base más reducido comparado con otras armas a distancia.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * 
 * @version 1.0
 * @see Arma
 */
public class Pistola extends Arma {
    /**
     * Crea una nueva instancia de Pistola con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Pistola</li>
     * <li><b>Daño:</b> 20</li>
     * <li><b>Precisión:</b> 70%</li>
     * </ul>
     * </p>
     */
    public Pistola() {
        super("Pistola", 20, 70);
    }

}
