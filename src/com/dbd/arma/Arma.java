package com.dbd.arma;

public abstract class Arma {
    protected String nombreArma;
    protected int danioBase;

    public Arma(String nombreArma, int danioBase) {
        this.nombreArma = nombreArma;
        this.danioBase = danioBase;
    }

    public abstract void usar();
}