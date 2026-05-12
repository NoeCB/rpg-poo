package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Despiadado extends Perk {

    public Despiadado() {
        super("Súper Despiadado", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " canaliza pura rabia con Despiadado!");
        objetivo.recibirDanio(45);
    }
}
