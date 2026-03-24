package com.dbd.estados;

import com.dbd.entidades.Personaje;
import com.dbd.core.Util;

public class Hemorragia extends Estado {
    public Hemorragia(int turnosRestantes) {
        super("Hemorragia", turnosRestantes);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // Daño persistente cada turno
        int danioHemorragia = 5;
        System.out.println(Util.ROJO + objetivo.getNombrePersonaje() + " sangra profusamente y pierde " + danioHemorragia + " de vida." + Util.RESET);
        
        objetivo.setVidaActual(objetivo.getVidaActual() - danioHemorragia);
        
        this.turnosRestantes--;
    }
}
