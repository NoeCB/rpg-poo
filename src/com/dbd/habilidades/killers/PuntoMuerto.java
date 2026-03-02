package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class PuntoMuerto extends Perk {
    
    public PuntoMuerto() {
        super("Punto Muerto", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " paraliza el avance del rival con Punto Muerto.");
        
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        caster.setDefendiendo(true);
        
        System.out.println("La Entidad sella el campo de batalla. La defensa aumenta y bloquea el próximo ataque.");
    }
}