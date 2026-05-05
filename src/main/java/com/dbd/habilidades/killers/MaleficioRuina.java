package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class MaleficioRuina extends Perk {

    public MaleficioRuina() {
        super("Maleficio: Ruina", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("Un tótem maldito emite una intensa luz roja. Maleficio: Ruina ha sido activado.");
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        objetivo.recibirDanio(15);
        System.out.println("El asesino se fortalece inmensamente frente a la desgracia ajena.");
    }
}
