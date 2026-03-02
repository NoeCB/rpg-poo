package com.dbd.entidades;

public class SteveHarrington extends Personaje {
    public SteveHarrington() {
        super("Steve Harrington", 110, 110, 10, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " se arregla el pelo y busca a alguien a quien proteger.");
    }
}