package com.dbd.entidades;

public class FengMin extends Personaje {
    public FengMin() {
        super("Feng Min", 100, 100, 10, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " empieza a reparar un motor a toda velocidad.");
    }
}