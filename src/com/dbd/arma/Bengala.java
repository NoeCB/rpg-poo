package com.dbd.arma;

/**
 * Representa una Bengala usada para señalizar y defenderse.
 * Arma típica de superviviente en DbD.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Bengala extends Arma {

    public Bengala() {
        super("Bengala", 8, 95);
    }

    @Override
    public void usar() {
        System.out.println("¡Una luz roja intensa ilumina el campo de batalla, deslumbrando y quemando!");
    }
}
