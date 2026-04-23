package com.dbd.entidades;

/**
 * Representa al infame asesino Ghoul.
 * <p>
 * Resistencia superior con 160 HP y defensa moderada.
 *
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Ghoul extends Personaje {

    public Ghoul() {
        super("Ghoul", 160, 160, 15, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " emite un rugido desgarrador mientras gotea toxinas letales de sus fauces.");
    }
}
