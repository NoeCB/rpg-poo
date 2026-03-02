package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

public class AbrazoLugubre extends Perk {
    
    public AbrazoLugubre() {
        super("Abrazo Lúgubre", 1);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " cierra sus garras con Abrazo Lúgubre.");
        
        System.out.println("Drena la esperanza de " + objetivo.getNombrePersonaje() + ", robándole vida.");
        objetivo.recibirDanio(20);
        
        caster.setVidaActual(caster.getVidaActual() + 30);
        System.out.println("La Entidad complace al asesino, curándole 30 HP.");
    }
}