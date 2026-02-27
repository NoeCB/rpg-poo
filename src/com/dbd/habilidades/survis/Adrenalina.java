package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class Adrenalina extends Perk {
    
    public Adrenalina() {
        super("Adrenalina", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡Se enciende el último motor! " + caster.getNombrePersonaje() + " recibe un subidón de Adrenalina.");
        
        // Cura masiva y subida de defensa
        caster.setVidaActual(caster.getVidaActual() + 60);
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        
        System.out.println("Recupera 60 HP al instante y su defensa aumenta permanentemente. ¡Está listo para escapar!");
    }
}