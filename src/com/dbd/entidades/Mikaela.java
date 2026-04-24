package com.dbd.entidades;

/**
 * Representa a Mikaela Reid, una superviviente que utiliza el misticismo.
 * <p>
 * Vida de 100, defensa moderada-baja de 10.
 *
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Mikaela extends Personaje {

    public Mikaela() {
        super("Mikaela Reid", 100, 100, 10);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " enciende unas hierbas místicas y susurra un cántico protector.");
    }
}
