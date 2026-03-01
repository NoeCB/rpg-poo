package com.dbd.entidades;

public class Animatronico extends Personaje {
    public Animatronico() {
        super("Animatrónico", 220, 220, 20, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " hace un ruido metálico ensordecedor (Jumpscare!).");
    }
}