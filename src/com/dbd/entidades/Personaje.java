package com.dbd.entidades;

import java.util.ArrayList;

public abstract class Personaje {
    protected String nombrePersonaje;
    protected int vidaActual;
    protected int vidaMax;
    protected int defensaBase;
    protected int puntosSangre;
    //pienso que estados y perks pueden ser clases, pero que por ahora sean strings para simplificar el código.
    protected ArrayList<String> estados;
   protected ArrayList<String> perks;

    

    public Personaje(String nombrePersonaje, int vidaActual, int vidaMax, int defensaBase, int puntosSangre) {
        this.nombrePersonaje = nombrePersonaje;
        this.vidaActual = vidaActual;
        this.vidaMax = vidaMax;
        this.defensaBase = defensaBase;
        this.puntosSangre = puntosSangre;
        this.estados = new ArrayList<>();
        this.perks = new ArrayList<>();
    }

    public abstract void accion();
}
