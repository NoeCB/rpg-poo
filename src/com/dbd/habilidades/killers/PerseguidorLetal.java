package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class PerseguidorLetal extends Perk {
    
    public PerseguidorLetal() {
        super("Perseguidor Letal", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " lee las auras con Perseguidor Letal.");
        
        System.out.println("Conoce exactamente dónde golpear. ¡Lanza un ataque preciso y letal!");
        objetivo.recibirDanio(25);
        
        caster.setVidaActual(caster.getVidaActual() + 15);
        System.out.println("La adrenalina de la caza temprana le cura 15 HP.");
    }
}