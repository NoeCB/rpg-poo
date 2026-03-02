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

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public int getVidaBase() {
        return vidaBase;
    }

    public void setVidaBase(int vidaBase) {
        this.vidaBase = vidaBase;
    }

    public void setDanioBase(int danioBase) {
        this.danioBase = danioBase;
    }

    public int getDanioBase() {
        return danioBase;
    }
    //recibe daño de un ataque
    public void recibirDanio(int danio) {
    this.vidaBase = this.vidaBase - danio; 
    if (this.vidaBase < 0) {
        this.vidaBase = 0;
    }
    
    System.out.println(this.nombrePersonaje + " ha recibido " + danio + " puntos de daño. Vida restante: " + this.vidaBase);
}

}

// public ArrayList<Perk> getPerks() {
// return perks;
// }

// public void aplicarEstado(Estado nuevoEstado) {
// this.estados.add(nuevoEstado);
// }

// public void procesarEstados() {
// for (int i = estados.size() - 1; i >= 0; i--) {
// Estado e = estados.get(i);
// e.aplicarEfecto(this); // Aplica el daño o la cura

// if (e.getTurnosRestantes() <= 0) {
// estados.remove(i); // Borra el estado si se acaba el tiempo
// }
// }
// }

// public abstract void accion();
//
