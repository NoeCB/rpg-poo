package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class DejaVu extends Perk {
    
    public DejaVu() {
        super("Déjà Vu", 1); // 1 uso, buff permanente
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " analiza el entorno con Déjà Vu y traza un plan perfecto.");
        
        // Sube su defensa base y hace un ataque táctico
        caster.setDefensaBase(caster.getDefensaBase() + 15);
        System.out.println("Su defensa aumenta permanentemente. ¡Conoce el terreno!");
        
        System.out.println("Aprovecha su posicionamiento para golpear a " + objetivo.getNombrePersonaje() + " con el entorno.");
        objetivo.recibirDanio(15);
    }
}