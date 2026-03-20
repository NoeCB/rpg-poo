package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class FuriaDelEspiritu extends Perk {
    
    public FuriaDelEspiritu() {
        super("Furia del Espíritu", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " desata la Furia del Espíritu.");
        
        // Destruye la defensa (los recursos) y ataca
        int defensaPerdida = Math.max(0, objetivo.getDefensaBase() - 20);
        objetivo.setDefensaBase(defensaPerdida);
        
        System.out.println("¡Destruye la protección de " + objetivo.getNombrePersonaje() + " por completo!");
        objetivo.recibirDanio(30);
    }
}