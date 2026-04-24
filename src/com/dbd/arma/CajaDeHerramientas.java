package com.dbd.arma;

/**
 * Representa la clásica Caja de Herramientas usada improvisadamente como contusión.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class CajaDeHerramientas extends Arma {

    public CajaDeHerramientas() {
        super("Caja de Herramientas", 18, 70);
    }

    @Override
    public void usar() {
        System.out.println("El superviviente arroja o golpea fuertemente con la pesada caja de herramientas.");
    }
}
