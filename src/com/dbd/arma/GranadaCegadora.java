package com.dbd.arma;

/**
 * Representa una Granada Cegadora armada explosivamente para cegar.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class GranadaCegadora extends Arma {

    public GranadaCegadora() {
        super("Granada Cegadora", 12, 85);
    }

    @Override
    public void usar() {
        System.out.println("¡BOOM! Un destello cegador inunda la zona aturdiendo al objetivo.");
    }
}
