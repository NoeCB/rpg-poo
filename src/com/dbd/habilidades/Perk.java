package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public abstract class Perk {
    protected String nombre;
    protected int usos;

    public Perk(String nombre, int usos) {
        this.nombre = nombre;
        this.usos = usos;
    }

    public String getNombre() {
        return this.nombre;
    }

    // El método que todas las habilidades hijas están obligadas a programar
    public abstract void lanzar(Personaje caster, Personaje objetivo);

    public int getUsos() {
        return this.usos;
    }

    // AÑADE ESTE MÉTODO NUEVO:
    public void consumirUso() {
        if (this.usos > 0) {
            this.usos--;
        }
    }
}