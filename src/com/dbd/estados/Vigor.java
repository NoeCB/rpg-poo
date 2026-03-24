package com.dbd.estados;

import com.dbd.entidades.Personaje;
import com.dbd.core.Util;

public class Vigor extends Estado {
    public Vigor(int turnosRestantes) {
        super("Vigor", turnosRestantes);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // Cura un poco de vida cada turno
        int curacion = 8;
        System.out.println(Util.VERDE + objetivo.getNombrePersonaje() + " siente un subidón de Vigor y recupera " + curacion + " de vida." + Util.RESET);
        
        objetivo.setVidaActual(objetivo.getVidaActual() + curacion);
        
        if (objetivo.getVidaActual() > objetivo.getVidaMax()) {
            objetivo.setVidaActual(objetivo.getVidaMax());
        }
        
        this.turnosRestantes--;
    }
}
