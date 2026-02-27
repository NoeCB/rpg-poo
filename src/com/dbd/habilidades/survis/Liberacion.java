package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Liberacion extends Perk {
    
    public Liberacion() {
        super("Liberación", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se descuelga a sí mismo gracias a Liberación.");
        
        caster.setVidaActual(caster.getVidaActual() + 30);
        caster.setDefendiendo(true); // Se asegura de bloquear el siguiente ataque
        
        System.out.println("Recupera 30 HP y adopta una postura defensiva perfecta para huir del asesino.");
    }
}