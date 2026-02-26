package com.dbd.arma;

public abstract class Arma {
    protected String nombreArma;
    protected int danioBase;
    protected int precision;

    public Arma(String nombreArma, int danioBase, int precision) {
        this.nombreArma = nombreArma;
        this.danioBase = danioBase;
        this.precision = precision;
    }

    public String getNombreArma() {
        return nombreArma;
    }

    public void setNombreArma(String nombreArma) {
        this.nombreArma = nombreArma;
    }

    public abstract void usar();
}