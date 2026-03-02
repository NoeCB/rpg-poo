package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class DemuestraLoQueVales extends Perk {
    
    public DemuestraLoQueVales() {
        super("Demuestra lo que vales", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se llena de determinación con Demuestra lo que vales.");
        
        // Cura media y ataque contundente
        caster.setVidaActual(caster.getVidaActual() + 30);
        System.out.println("Su motivación le restaura 30 HP y asesta un golpe coordinado.");
        objetivo.recibirDanio(25);
    }
}