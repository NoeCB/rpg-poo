package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Resonancia del Dolor
 * 
 * Perk de nivel 4 que permite al asesino activar un gancho escandaloso,
 * causando una explosión brutal que sacude al superviviente.
 * 
 * Efectos:
 * - Activa un gancho escandaloso
 * - Causa una explosión brutal
 * - Inflige 35 puntos de daño al objetivo
 * - Puede ser utilizado hasta 4 veces (utiliza 4 tokens)
 */
public class ResonanciaDelDolor extends Perk {
    
    /**
     * Constructor de ResonanciaDelDolor
     * 
     * Inicializa la habilidad con el nombre "Resonancia del Dolor" y 4 usos disponibles.
     * En Dead by Daylight, esta habilidad utiliza 4 tokens que se regeneran rápidamente.
     */
    public ResonanciaDelDolor() {
        super("Resonancia del Dolor", 4); // En DbD tiene 4 tokens y se puede usar 4 veces
    }

    /**
     * Ejecuta el efecto de Resonancia del Dolor
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Activa un gancho escandaloso
     * 2. Causa una explosión brutal que sacude al objetivo
     * 3. Inflige 35 puntos de daño al objetivo
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡" + caster.getNombrePersonaje() + " activa un gancho escandaloso: resonancia del dolor!");
        
        System.out.println("Una explosión brutal sacude a " + objetivo.getNombrePersonaje() + " quitándole 35 HP.");
        objetivo.recibirDanio(35);
    }
}