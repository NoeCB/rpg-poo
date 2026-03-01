package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Agilidad extends Perk {
    
    public Agilidad() {
        super("Agilidad", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " salta un obstáculo rápidamente con Agilidad.");
        
        // Daño táctico al killer y defensa para el superviviente
        System.out.println("El obstáculo frena a " + objetivo.getNombrePersonaje() + " causándole 15 de daño.");
        objetivo.recibirDanio(15);
        caster.setDefendiendo(true);
    }
}