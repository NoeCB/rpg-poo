package com.dbd.arma;

/**
 * Arma cuerpo a cuerpo estándar pesada usada por asesinos brutales.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class CuchilloDeCarnicero extends Arma {

    public CuchilloDeCarnicero() {
        super("Cuchillo de Carnicero", 22, 80);
    }

    @Override
    public void usar() {
        System.out.println("Un tajo brutal desciende trazando un arco mortal.");
    }
}
