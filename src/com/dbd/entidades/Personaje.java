package com.dbd.entidades;

public abstract class Personaje {
    protected String nombrePersonaje;

    protected int vidaBase;
    protected int defensaBase;
    protected int puntosSangre;

    public Personaje(String nombrePersonaje, int vidaBase, int defensaBase, int puntosSangre) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaBase = vidaBase;
        this.defensaBase = defensaBase;
        this.puntosSangre = puntosSangre;
    }

    public abstract void accion();
}
