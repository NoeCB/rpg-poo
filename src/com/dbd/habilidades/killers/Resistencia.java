package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Resistencia
 * 
 * Perk de nivel 2 que permite al asesino apretarse los puños y activar
 * un aumentando de resistencia, mejorando su salud y defensa.
 * 
 * Efectos:
 * - Aprietael puño para aumentar resistencia
 * - Recupera 25 puntos de vida
 * - Aumenta la defensa base del asesino en 15 puntos de forma permanente
 * - Reduce el daño futuro al asesino
 * - Puede ser utilizado hasta 2 veces
 */
public class Resistencia extends Perk {
    
    public Resistencia() {
        super("Resistencia", 2);
    }

    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " aprieta los puños y activa Resistencia.");
        
        caster.setVidaActual(caster.getVidaActual() + 25);
        caster.setDefensaBase(caster.getDefensaBase() + 15);
        
        System.out.println("¡Es imparable! Se cura 25 HP y su defensa aumenta. Los ataques le hacen menos mella.");
    }
}