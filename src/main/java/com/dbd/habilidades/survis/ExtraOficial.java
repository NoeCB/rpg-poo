package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Extraoficial
 * 
 * Perk de nivel 2 que permite al superviviente volverse silencioso,
 * ocultando su aura y mejorando su salud y defensa.
 * 
 * Efectos:
 * - Se vuelve silencioso e invisible al asesino
 * - Oculta su aura completamente
 * - Recupera 25 puntos de vida
 * - Aumenta la defensa base del superviviente en 10 puntos de forma permanente
 * - Puede ser utilizado hasta 2 veces
 */
public class ExtraOficial extends Perk {
    
    /**
     * Constructor de ExtraOficial
     * 
     * Inicializa la habilidad con el nombre "Extraoficial" y 2 usos disponibles.
     */
    public ExtraOficial() {
        super("Extraoficial", 2);
    }

    /**
     * Ejecuta el efecto de Extraoficial
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Se vuelve silencioso e invisible
     * 2. Oculta completamente su aura
     * 3. Recupera 25 puntos de vida
     * 4. Aumenta la defensa base del superviviente en 10 puntos permanentemente
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se vuelve silencioso gracias a Extraoficial.");
        
        caster.setVidaActual(caster.getVidaActual() + 25);
        caster.setDefensaBase(caster.getDefensaBase() + 10);
        
        System.out.println("Su aura está oculta. Recupera 25 HP y su defensa aumenta permanentemente +10.");
    }
}