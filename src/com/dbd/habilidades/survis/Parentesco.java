package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Parentesco extends Perk {
    
    public Parentesco() {
        super("Parentesco", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " revela el aura del asesino usando Parentesco.");
        
        caster.setVidaActual(caster.getVidaActual() + 20);
        caster.setDefensaBase(caster.getDefensaBase() + 10);
        caster.setDefendiendo(true);
        
        System.out.println("Predice el próximo ataque perfectamente. Recupera 20 HP y aumenta su defensa base.");
    }
}