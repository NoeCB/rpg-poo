package com.dbd.estados;

import com.dbd.entidades.Personaje;

public abstract class Estado {
    protected String nombre;
    protected int turnosRestantes;

    public Estado(String nombre, int turnosRestantes) {
        this.nombre = nombre;
        this.turnosRestantes = turnosRestantes;
    }

    public String getNombre() { return nombre; }
    public int getTurnosRestantes() { return turnosRestantes; }

    public abstract void aplicarEfecto(Personaje objetivo);
}