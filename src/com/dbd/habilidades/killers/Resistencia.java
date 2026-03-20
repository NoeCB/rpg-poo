package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Resistencia extends Perk {
    
    public Resistencia() {
        super("Resistencia", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " aprieta los puños y activa Resistencia.");
        
        caster.setVidaActual(caster.getVidaActual() + 25);
        caster.setDefensaBase(caster.getDefensaBase() + 15);
        
        System.out.println("¡Es imparable! Se cura 25 HP y su defensa aumenta. Los ataques le hacen menos mella.");
    }
}