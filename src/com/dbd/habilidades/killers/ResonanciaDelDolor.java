package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class ResonanciaDelDolor extends Perk {
    
    public ResonanciaDelDolor() {
        super("Resonancia del Dolor", 4); // En DbD tiene 4 tokens y se puede usar 4 veces
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡" + caster.getNombrePersonaje() + " activa un gancho escandaloso: resonancia del dolor!");
        
        System.out.println("Una explosión brutal sacude a " + objetivo.getNombrePersonaje() + " quitándole 35 HP.");
        objetivo.recibirDanio(35);
    }
}