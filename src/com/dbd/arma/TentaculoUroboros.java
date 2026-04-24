package com.dbd.arma;

/**
 * Tentáculo mutante extendible portador del virus Uroboros asestado por el Cerebro.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class TentaculoUroboros extends Arma {

    public TentaculoUroboros() {
        super("Tentáculo Uroboros", 26, 65); // Daño muy alto, precisión baja
    }

    @Override
    public void usar() {
        System.out.println("El tentáculo del Uroboros se abalanza extendiéndose velozmente hacia la presa.");
    }
}
