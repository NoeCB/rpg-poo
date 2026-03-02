package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public abstract class Perk {
    
    protected String nombrePerk;
    


    public Perk(String nombrePerk) {
        this.nombrePerk = nombrePerk;
        
    }

    public String getNombrePerk() {
        return nombrePerk;
    }
    public void setNombrePerk(String nombrePerk) {
        this.nombrePerk = nombrePerk;
    }

    // El motor llamará a este método sin saber qué perk es (Polimorfismo) 
    public abstract void lanzar(Personaje caster, Personaje objetivo);
}