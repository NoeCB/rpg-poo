package com.dbd.entidades;

public class Superviviente extends Personaje {

    public Superviviente(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre) {
        
        super(nombrePersonaje, vidaActual, vidaMax, defensaBase, puntosSangre);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " está buscando un objetivo o reparando...");
    }
}