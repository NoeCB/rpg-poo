package com.dbd.estados;

import com.dbd.entidades.Personaje;
import com.dbd.core.Util;

public class Ceguera extends Estado {
    public Ceguera(int turnosRestantes) {
        super("Ceguera", turnosRestantes);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // Desorienta al objetivo quitándole un poco de defensa si es posible
        System.out.println(Util.CYAN + objetivo.getNombrePersonaje() + " sufre Ceguera y se desorienta en el combate." + Util.RESET);
        
        if (objetivo.getDefensaBase() > 0) {
            objetivo.setDefensaBase(objetivo.getDefensaBase() - 2);
        }
        
        this.turnosRestantes--;
    }
}
