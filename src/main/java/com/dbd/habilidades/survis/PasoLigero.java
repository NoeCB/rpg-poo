package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class PasoLigero extends Perk {

    public PasoLigero() {
        super("Paso Ligero", 4);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " camina con Paso Ligero sin dejar marcas de arañazos.");
        caster.setDefensaBase(caster.getDefensaBase() + 10);
        caster.setDefendiendo(true);
    }
}
