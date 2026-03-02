package com.dbd.habilidades;

import com.dbd.entidades.Personaje;

public abstract class Perk {
<<<<<<< HEAD
    
    protected String nombrePerk;
    


    public Perk(String nombrePerk) {
        this.nombrePerk = nombrePerk;
        
    }

    public String getNombrePerk() {
        return nombrePerk;
    }
    public void setNombrePerk(String nombrePerk) {
        this.nombrePerk = nombrePerk;
=======
    protected String nombre;
    protected int usos;

    public Perk(String nombre, int usos) {
        this.nombre = nombre;
        this.usos = usos;
    }

    public String getNombre() {
        return this.nombre;
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
    }

    public int getUsos() {
        return this.usos;
    }

    // El método que todas las habilidades hijas están obligadas a programar
    public abstract void lanzar(Personaje caster, Personaje objetivo);
}