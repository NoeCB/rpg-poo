package com.dbd.arma;
/**
 * Representa un arma de naturaleza espectral o maldita.
 * <p>
 * El {@code VinculoDeCondena} destaca por su altísima precisión, permitiendo
 * asegurar impactos casi garantizados, aunque su daño base sea moderado.
 * Es ideal para efectos de control o desgaste constante.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * @version 1.0
 * @see Arma
 */

public class VinculoDeCondena extends Arma {
    /**
     * Crea una nueva instancia de Vinculo de Condena con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Vinculo de Condena</li>
     * <li><b>Daño:</b> 10</li>
     * <li><b>Precisión:</b> 90%</li>
     * </ul>
     * </p>
     */
    public VinculoDeCondena() {
        // Nombre, Daño, Munición, Precisión
        super("Vinculo de Condena", 10, 90);
    }

    @Override
    public void usar() {
      
    }
}
