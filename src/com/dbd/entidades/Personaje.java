package com.dbd.entidades;

public abstract class Personaje {
    protected String nombrePersonaje;

    protected int vidaBase;
    protected int defensaBase;
    protected int danioBase;

    public Personaje(String nombrePersonaje, int vidaBase, int defensaBase, int danioBase) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaBase = vidaBase;
        this.defensaBase = defensaBase;
        this.danioBase = danioBase;
    }

    public abstract void accion();
}
