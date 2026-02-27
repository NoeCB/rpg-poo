package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class LoLograremos extends Perk {
    
    public LoLograremos() {
        super("Lo Lograremos", 2); // 2 usos por partida
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("\"¡No te rindas!\" " + caster.getNombrePersonaje() + " activa Lo Lograremos.");
        
        // Curación masiva
        caster.setVidaActual(caster.getVidaActual() + 80);
        System.out.println("¡Curación ultra rápida! Recupera 80 HP y vuelve al combate con fuerza.");
    }
}