package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Agilidad
 * 
 * Perk de nivel 3 que permite al superviviente saltar ágilmente sobre obstáculos,
 * frenando al asesino mientras se pone en postura defensiva.
 * 
 * Efectos:
 * - Inflige 15 puntos de daño al asesino que intenta perseguir
 * - Activa la postura defensiva del superviviente
 * - Puede ser utilizado hasta 3 veces
 */
public class Agilidad extends Perk {
    
    /**
     * Constructor de Agilidad
     * 
     * Inicializa la habilidad con el nombre "Agilidad" y 3 usos disponibles.
     */
    public Agilidad() {
        super("Agilidad", 3);
    }

    /**
     * Ejecuta el efecto de Agilidad
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo el personaje que recibe el efecto (asesino)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de sobresalto del superviviente
     * 2. Inflige 15 puntos de daño al asesino
     * 3. Activa el modo defensivo del superviviente
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " salta un obstáculo rápidamente con Agilidad.");
        
        // Daño táctico al killer y defensa para el superviviente
        System.out.println("El obstáculo frena a " + objetivo.getNombrePersonaje() + " causándole 15 de daño.");
        objetivo.recibirDanio(15);
        caster.setDefendiendo(true);
    }
}