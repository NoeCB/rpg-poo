package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class PersonajeSecundario extends Perk {
    
    public PersonajeSecundario() {
        super("Personaje Secundario", 2); 
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡" + caster.getNombrePersonaje() + " sale disparado al 150% de velocidad con Personaje Secundario!");
        
        // Daño moderado-alto y defensa automática
        System.out.println("¡Ciega y golpea a " + objetivo.getNombrePersonaje() + " en un abrir y cerrar de ojos!");
        objetivo.recibirDanio(30);
        caster.setDefendiendo(true);
    }
}