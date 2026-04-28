package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class TomarDecision extends Perk {

    public TomarDecision() {
        super("Toma tu Decisión", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("\"Toma tu Decisión\"... " + caster.getNombrePersonaje() + " expone letalmente a la presa.");
        int reduccion = objetivo.getDefensaBase() / 2;
        objetivo.setDefensaBase(objetivo.getDefensaBase() - reduccion);
        System.out.println("La defensa del objetivo se reduce a la mitad.");
    }
}
