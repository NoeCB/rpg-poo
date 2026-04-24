package com.dbd.arma;

/**
 * Cuchilla retráctil letal y altamente precisa usada por La Cerda.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class CuchillaOculta extends Arma {

    public CuchillaOculta() {
        super("Cuchilla Oculta", 16, 95);
    }

    @Override
    public void usar() {
        System.out.println("SNAP! La cuchilla emerge y penetra la carne antes de que la víctima reaccione.");
    }
}
