package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Inquebrantable extends Perk {
    
    public Inquebrantable() {
        super("Inquebrantable", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        double porcentajeVida = (double) caster.getVidaActual() / caster.getVidaMax();
        
        if (porcentajeVida <= 0.25) {
            System.out.println(caster.getNombrePersonaje() + " se niega a caer y activa Inquebrantable.");
            caster.setVidaActual(caster.getVidaActual() + 50);
            System.out.println("Se recupera del estado crítico ganando 50 HP.");
        } else {
            System.out.println(caster.getNombrePersonaje() + " intenta usar Inquebrantable, pero aún no está en estado crítico.");
        }
    }
}