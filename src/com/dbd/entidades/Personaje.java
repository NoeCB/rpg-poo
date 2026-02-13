package com.dbd.entidades;

public abstract class Personaje {
    public String nombre;

    public Personaje(String nombre){
        this.nombre = nombre;
    }

    public abstract void accion();
}
