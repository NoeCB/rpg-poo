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

    public int getUsos() {
        return this.usos;
    }

    // El método que todas las habilidades hijas están obligadas a programar
    public abstract void lanzar(Personaje caster, Personaje objetivo);
}