package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public abstract class Perk {
    
    protected String nombre;
    protected int costeSangre; // El recurso que consume 

    public Perk(String nombre, int costeSangre) {
        this.nombre = nombre;
        this.costeSangre = costeSangre;
    }

    public String getNombre() {
        return nombre;
    }

    // El motor llamará a este método sin saber qué perk es (Polimorfismo) 
    public abstract void lanzar(Personaje caster, Personaje objetivo);
}