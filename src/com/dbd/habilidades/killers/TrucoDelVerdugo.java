package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class TrucoDelVerdugo extends Perk {

    public TrucoDelVerdugo() {
        super("Truco del Verdugo", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " usa el Truco del Verdugo revelando trampas mortales.");
        objetivo.recibirDanio(35);
        caster.setDefendiendo(true);
    }
}
