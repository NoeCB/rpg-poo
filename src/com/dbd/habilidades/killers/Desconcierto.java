package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Desconcierto extends Perk {
    
    public Desconcierto() {
        super("Desconcierto", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " bloquea las rutas de escape con Desconcierto.");
        
        // Reducimos la defensa del superviviente (asegurando que no baje de 0)
        int defensaPerdida = Math.max(0, objetivo.getDefensaBase() - 10);
        objetivo.setDefensaBase(defensaPerdida);
        
        System.out.println("¡" + objetivo.getNombrePersonaje() + " se queda sin recursos! Su defensa base se reduce permanentemente.");
        objetivo.recibirDanio(15);
    }
}