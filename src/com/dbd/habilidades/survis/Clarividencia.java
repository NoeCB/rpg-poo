package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Clarividencia extends Perk {

    public Clarividencia() {
        super("Clarividencia", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " usa Clarividencia, aumentando sus reflejos evasivos mágicamente.");
        caster.setDefensaBase(caster.getDefensaBase() + 15);
    }
}
