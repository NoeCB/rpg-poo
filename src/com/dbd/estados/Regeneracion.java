package com.dbd.estados;

import com.dbd.entidades.Personaje;

public class Regeneracion extends Estado {
    public Regeneracion(int turnos) {
        super("Regeneración", turnos);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        System.out.println(objetivo.getNombrePersonaje() + " recupera 15 de vida por Regeneración.");
        objetivo.setVidaActual(objetivo.getVidaActual() + 15);
        this.turnosRestantes--;
    }
}