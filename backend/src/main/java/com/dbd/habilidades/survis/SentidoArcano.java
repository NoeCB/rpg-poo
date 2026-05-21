package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class SentidoArcano extends Perk {

    public SentidoArcano() {
        super("Sentido Arcano", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("Los sentidos de " + caster.getNombrePersonaje() + " perciben al asesino desde las sombras.");
        objetivo.setDefensaBase(Math.max(0, objetivo.getDefensaBase() - 5));
        System.out.println("La defensa del objetivo se reduce.");
    }
}
