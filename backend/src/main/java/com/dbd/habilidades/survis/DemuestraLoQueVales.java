package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Demuestra lo que vales
 * 
 * Perk de nivel 2 que permite al superviviente llenarse de determinación,
 * recuperándose y asestando un golpe contundente coordinado al asesino.
 * 
 * Efectos:
 * - Se llena de determinación y motivación
 * - Recupera 30 puntos de vida
 * - Asesta un golpe coordinado contundente
 * - Inflige 25 puntos de daño al asesino
 * - Puede ser utilizado hasta 2 veces
 */
public class DemuestraLoQueVales extends Perk {
    
    /**
     * Constructor de DemuestraLoQueVales
     * 
     * Inicializa la habilidad con el nombre "Demuestra lo que vales" y 2 usos disponibles.
     */
    public DemuestraLoQueVales() {
        super("Demuestra lo que vales", 2);
    }

    /**
     * Ejecuta el efecto de Demuestra lo que vales
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo el personaje que recibe el efecto (asesino)
     * 
     * Acciones realizadas:
     * 1. Se llena de determinación y motivación
     * 2. Recupera 30 puntos de vida
     * 3. Asesta un golpe coordinado contundente
     * 4. Inflige 25 puntos de daño al asesino
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se llena de determinación con Demuestra lo que vales.");
        
        // Cura media y ataque contundente
        caster.setVidaActual(caster.getVidaActual() + 30);
        System.out.println("Su motivación le restaura 30 HP y asesta un golpe coordinado.");
        objetivo.recibirDanio(25);
    }
}