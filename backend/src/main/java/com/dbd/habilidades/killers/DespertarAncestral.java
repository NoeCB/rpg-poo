package com.dbd.habilidades.killers;

import com.dbd.core.UtilEstados;
import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class DespertarAncestral extends Perk {

    public DespertarAncestral() {
        super("Despertar Ancestral", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " saca partido de su Despertar Ancestral para consumir la fuerza del rival.");
        // Usa la mejora UtilEstados para robo de HP
        UtilEstados.transferenciaVital(objetivo, caster, 20);
    }
}
