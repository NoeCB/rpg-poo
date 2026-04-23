package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;
import com.dbd.estados.Escudo;

public class AgenteEncubierto extends Perk {

    public AgenteEncubierto() {
        super("Agente Encubierto", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " actúa como un Agente Encubierto y despliega defensas absolutas.");
        // Utiliza la nueva mejora Escudo 
        caster.aplicarEstado(new Escudo(2));
    }
}
