package com.dbd.entidades;

public class Onryo extends Personaje {
    public Onryo() {
        super("Onryo", 220, 220, 20, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " sale de un televisor cercano.");
    }
}