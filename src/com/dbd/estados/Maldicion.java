package com.dbd.estados;

import com.dbd.entidades.Personaje;

public class Maldicion extends Estado {
    public Maldicion(int turnos) {
        super("Maldición", turnos);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        System.out.println(objetivo.getNombrePersonaje() + " sufre 10 de daño por la Maldición.");
        objetivo.setVidaActual(objetivo.getVidaActual() - 10);
        this.turnosRestantes--;
    }
}