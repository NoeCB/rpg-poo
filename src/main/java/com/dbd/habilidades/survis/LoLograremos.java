package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Lo Lograremos
 * 
 * Perk de nivel 2 que permite al superviviente motivar a su equipo,
 * proporcionando una curación ultra rápida y masiva.
 * 
 * Efectos:
 * - Motiva al equipo de supervivientes
 * - Curación ultra rápida y masiva
 * - Recupera 80 puntos de vida
 * - Devuelve el superviviente al combate con fuerza renovada
 * - Puede ser utilizado hasta 2 veces
 */
public class LoLograremos extends Perk {
    
    /**
     * Constructor de LoLograremos
     * 
     * Inicializa la habilidad con el nombre "Lo Lograremos" y 2 usos disponibles.
     * Esta habilidad es muy potente en curación, permitiendo hasta 2 activaciones por partida.
     */
    public LoLograremos() {
        super("Lo Lograremos", 2); // 2 usos por partida
    }

    /**
     * Ejecuta el efecto de Lo Lograremos
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Motiva al equipo de supervivientes
     * 2. Activa curación ultra rápida y masiva
     * 3. Recupera 80 puntos de vida
     * 4. El superviviente vuelve al combate con fuerza renovada
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("\"¡No te rindas!\" " + caster.getNombrePersonaje() + " activa Lo Lograremos.");
        
        // Curación masiva
        caster.setVidaActual(caster.getVidaActual() + 80);
        System.out.println("¡Curación ultra rápida! Recupera 80 HP y vuelve al combate con fuerza.");
    }
}