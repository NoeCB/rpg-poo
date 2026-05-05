package com.dbd.habilidades.killers;

import com.dbd.estados.Locura;
import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Erupcion extends Perk {

    public Erupcion() {
        super("Erupción", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " activa Erupción. ¡Un estallido ensordecedor resuena!");
        // Utiliza la nueva mejora Locura
        objetivo.aplicarEstado(new Locura(2));
        System.out.println(objetivo.getNombrePersonaje() + " es afligido con un estado de Locura.");
    }
}
