package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Fajador extends Perk {
    
    public Fajador() {
        super("Fajador", 2); // Solo 2 usos, es muy fuerte
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        // Calculamos si está herido
        double porcentajeVida = (double) caster.getVidaActual() / caster.getVidaMax();
        
        if (porcentajeVida <= 0.50) {
            System.out.println(caster.getNombrePersonaje() + " aprieta los dientes y activa Fajador (Dead Hard)!");
            caster.setVidaActual(caster.getVidaActual() + 40);
            System.out.println("Gana Resistencia y recupera 40 HP al instante.");
        } else {
            System.out.println(caster.getNombrePersonaje() + " intenta usar Fajador, pero está demasiado sano para activarlo.");
        }
    }
}