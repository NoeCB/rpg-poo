package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Sacudida extends Perk {
    
    public Sacudida() {
        super("Sacudida", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " asesta un golpe demoledor que provoca una Sacudida!");
        
        objetivo.recibirDanio(35);
        System.out.println("La onda expansiva del ataque desorienta y castiga al objetivo.");
    }
}