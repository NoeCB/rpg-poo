package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class ExtraOficial extends Perk {
    
    public ExtraOficial() {
        super("Extraoficial", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se vuelve silencioso gracias a Extraoficial.");
        
        caster.setVidaActual(caster.getVidaActual() + 25);
        caster.setDefensaBase(caster.getDefensaBase() + 10);
        
        System.out.println("Su aura está oculta. Recupera 25 HP y su defensa aumenta permanentemente +10.");
    }
}