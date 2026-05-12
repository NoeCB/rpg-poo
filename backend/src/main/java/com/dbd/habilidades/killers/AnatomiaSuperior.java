package com.dbd.habilidades.killers;

import com.dbd.estados.Escudo;
import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class AnatomiaSuperior extends Perk {

    public AnatomiaSuperior() {
        super("Anatomía Superior", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("La Anatomía Superior de " + caster.getNombrePersonaje() + " intimida al rival y solidifica su postura.");
        caster.aplicarEstado(new Escudo(1));
    }
}
