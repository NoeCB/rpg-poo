package com.dbd.entidades;

public class SableWard extends Personaje {
    public SableWard() {
        // Nombre, vidaActual, vidaMax, defensaBase, puntosSangre
        super("Sable Ward", 250, 250, 30, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " se esconde en las sombras preparando un maleficio.");
    }
}