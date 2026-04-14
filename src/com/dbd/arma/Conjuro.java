package com.dbd.arma;
    /**
 * Representa un arma de tipo conjuro.
 * <p>
 * A diferencia de las armas físicas, el {@code Conjuro} ofrece un alto daño
 * y una precisión inferior al ser un arma a distancia.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * @version 1.0
 * @see Arma
 */

public class Conjuro extends Arma {

/**
     * Crea una nueva instancia de Conjuro Oscuro con valores predeterminados.
     * <p>
     * Las estadísticas base para este conjuro son:
     * <ul>
     * <li><b>Nombre:</b> Conjuro Oscuro</li>
     * <li><b>Daño:</b> 20</li>
     * <li><b>Precisión:</b> 60%</li>
     * </ul>
     * </p>
     */

    public Conjuro() {
        
        super("Conjuro Oscuro", 20, 60);
    }


    @Override
    public void usar() {
        
    }
}