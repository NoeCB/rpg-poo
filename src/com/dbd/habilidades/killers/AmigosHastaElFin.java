package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class AmigosHastaElFin extends Perk {
    
    public AmigosHastaElFin() {
        super("Amigos hasta el Fin", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se obsesiona con su objetivo usando Amigos hasta el Fin.");
        
        System.out.println("¡" + objetivo.getNombrePersonaje() + " queda EXPUESTO y recibe un golpe crítico masivo!");
        objetivo.recibirDanio(45); // Daño muy alto simulando el estado Expuesto
    }
}