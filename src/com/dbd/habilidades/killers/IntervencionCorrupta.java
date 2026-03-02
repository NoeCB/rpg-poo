package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class IntervencionCorrupta extends Perk {
    
    public IntervencionCorrupta() {
        super("Intervención Corrupta", 1); // Efecto pasivo/permanente, solo necesita 1 uso
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " invoca al Ente con Intervención Corrupta.");
        
        caster.setDefensaBase(caster.getDefensaBase() + 25);
        System.out.println("La niebla bloquea las rutas. La defensa del asesino aumenta en 25 puntos permanentemente.");
    }
}