package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Finesse extends Perk {

    public Finesse() {
        super("Finesse", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("Aprovechando su Finesse natural, " + caster.getNombrePersonaje() + " mitiga el pánico y adquiere una pose perfecta.");
        caster.setDefendiendo(true);
    }
}
