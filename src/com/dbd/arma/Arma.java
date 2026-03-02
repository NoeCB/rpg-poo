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

    public int getDanioBase() {
        return danioBase;
    }

    public void setDanioBase(int danioBase) {
        this.danioBase = danioBase;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public abstract void usar();
}