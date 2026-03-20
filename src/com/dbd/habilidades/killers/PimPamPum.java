package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class PimPamPum extends Perk {
    
    public PimPamPum() {
        super("Pim, pam, pum", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " usa Pim, pam, pum.");
        
        System.out.println("¡Asesta un golpe devastador que rompe las defensas de " + objetivo.getNombrePersonaje() + "!");
        objetivo.recibirDanio(30);
        caster.setDefendiendo(true); // Se prepara tras el gran golpe
    }
}