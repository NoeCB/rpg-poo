package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Oportunidades extends Perk {
    
    public Oportunidades() {
        super("Oportunidades", 1); // 1 uso porque el buff es permanente
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " usa Oportunidades y localiza los palés cercanos.");
        
        // Le subimos la defensa base al personaje
        int nuevaDefensa = caster.getDefensaBase() + 15;
        caster.setDefensaBase(nuevaDefensa);
        
        System.out.println("Su defensa aumenta permanentemente a " + nuevaDefensa + ".");
    }
}