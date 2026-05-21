package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Autocuracion extends Perk {

    public Autocuracion() {
        super("Autocuración", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se cura sin ayuda de botiquines.");
        int curacion = 25;
        caster.setVidaActual(Math.min(caster.getVidaActual() + curacion, caster.getVidaMax()));
        System.out.println("Ha recuperado " + curacion + " HP.");
    }
}
