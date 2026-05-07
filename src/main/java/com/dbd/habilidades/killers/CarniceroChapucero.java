package com.dbd.habilidades.killers;

import com.dbd.estados.Hemorragia;
import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class CarniceroChapucero extends Perk {

    public CarniceroChapucero() {
        super("Carnicero Chapucero", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("Los brutales ataques de " + caster.getNombrePersonaje() + " dejan heridas profundas (Carnicero Chapucero).");
        // Utilizamos el estado Hemorragia que ya existía orginalmente (de ahí que exista en dbd.estados)
        // Para aplicar un fuerte daño pasivo de varios turnos.
        objetivo.aplicarEstado(new Hemorragia(3));
        System.out.println("La hemorragia será persistente y severa.");
    }
}
