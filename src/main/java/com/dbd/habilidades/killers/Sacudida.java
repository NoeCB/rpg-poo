package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Sacudida
 * 
 * Perk de nivel 3 que permite al asesino asestar un golpe demoledor
 * que provoca una sacudida devastadora al objetivo.
 * 
 * Efectos:
 * - Golpe demoledor que causa una onda expansiva
 * - Inflige 35 puntos de daño
 * - Desorienta al objetivo con la onda expansiva
 * - Puede ser utilizado hasta 3 veces
 */
public class Sacudida extends Perk {
    
    /**
     * Constructor de Sacudida
     * 
     * Inicializa la habilidad con el nombre "Sacudida" y 3 usos disponibles.
     */
    public Sacudida() {
        super("Sacudida", 3);
    }

    /**
     * Ejecuta el efecto de Sacudida
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje del golpe demoledor
     * 2. Inflige 35 puntos de daño al objetivo
     * 3. Desorienta al objetivo con la onda expansiva
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " asesta un golpe demoledor que provoca una Sacudida!");
        
        objetivo.recibirDanio(35);
        System.out.println("La onda expansiva del ataque desorienta y castiga al objetivo.");
    }
}