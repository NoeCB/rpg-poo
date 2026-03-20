package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class BarbacoaYChile extends Perk {
    
    public BarbacoaYChile() {
        super("Barbacoa y Chile", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " revela las auras lejanas con Barbacoa y Chile.");
        
        if (objetivo.isDefendiendo()) {
            System.out.println(objetivo.getNombrePersonaje() + " es descubierto. ¡Su guardia no sirve de nada!");
            objetivo.setDefendiendo(false);
        }
        
        System.out.println("Lanza un ataque letal desde las sombras.");
        objetivo.recibirDanio(25);
        
        caster.setVidaActual(caster.getVidaActual() + 10);
        System.out.println("El sacrificio inminente le cura 10 HP.");
    }
}