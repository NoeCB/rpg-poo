package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Especialista extends Perk {

    public Especialista() {
        super("Especialista", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("Con el talento de un Especialista, " + caster.getNombrePersonaje() + " realiza una acción táctica rápida.");
        objetivo.recibirDanio(30);
    }
}
