package com.dbd.arma;

/**
 * Representa un arma de tipo cuerpo a cuerpo.
 * <p>
 * Esta clase es una implementación concreta de {@link Arma}.
 * Se caracteriza por ser un arma ágil y rápida, ideal para
 * combates a corta distancia, sacrificando poder de daño bruto
 * a cambio de mantener una buena probabilidad de acierto.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * 
 * @version 1.0
 * @see Arma
 */
public class Cuchillo extends Arma {

    /**
     * Crea una nueva instancia de Cuchillo con valores predeterminados.
     * <p>
     * Las estadísticas base para esta arma son:
     * <ul>
     * <li><b>Nombre:</b> Cuchillo de Caza</li>
     * <li><b>Daño:</b> 10</li>
     * <li><b>Precisión:</b> 80%</li>
     * </ul>
     * </p>
     */
    public Cuchillo() {
        // Daño bajo (10), precisión alta (80)
        super("Cuchillo de Caza", 10, 80);
    }

}