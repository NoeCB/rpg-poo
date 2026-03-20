package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class EnNingunLugarParaEsconderse extends Perk {
    
    public EnNingunLugarParaEsconderse() {
        super("En Ningún Lugar para Esconderse", 3);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " escudriña la zona con En Ningún Lugar para Esconderse.");
        
        if (objetivo.isDefendiendo()) {
            System.out.println("¡Descubre a " + objetivo.getNombrePersonaje() + " escondido y rompe su guardia!");
            objetivo.setDefendiendo(false); // Le quita el estado de defensa
        }
        
        System.out.println("Lanza un ataque directo y preciso.");
        objetivo.recibirDanio(20);
    }
}