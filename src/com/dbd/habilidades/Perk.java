package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public abstract class Perk {

    protected String nombre;
    protected int costeSangre;  //Coste del recurso para habilidades
    protected int coolDownMax;  //Turnos de espera para volver a usar habilidades

    public Perk(String nombre, int costeSangre){
        this.nombre = nombre;
        this.costeSangre = costeSangre;
    }

    public boolean puedeLanzarse(Personaje castear){
        return true;
    }

    public abstract void lanzar(Personaje castear, Personaje objetivo);

}
