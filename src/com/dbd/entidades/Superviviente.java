package com.dbd.entidades;

public class Superviviente extends Personaje {

    public Superviviente(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase) {
        super(nombrePersonaje, vidaActual, vidaMax, defensaBase);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " está buscando un objetivo...");
    }
}
