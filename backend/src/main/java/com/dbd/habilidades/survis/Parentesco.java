package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Parentesco
 * 
 * Perk de nivel 2 que permite al superviviente revelar el aura del asesino,
 * permitiendo predecir el próximo ataque con precisión.
 * 
 * Efectos:
 * - Revela el aura del asesino completamente
 * - Predice el próximo ataque del asesino
 * - Recupera 20 puntos de vida
 * - Aumenta la defensa base del superviviente en 10 puntos
 * - Activa el modo defensivo para bloquear el ataque predicho
 * - Puede ser utilizado hasta 2 veces
 */
public class Parentesco extends Perk {
    
    /**
     * Constructor de Parentesco
     * 
     * Inicializa la habilidad con el nombre "Parentesco" y 2 usos disponibles.
     */
    public Parentesco() {
        super("Parentesco", 2);
    }

    /**
     * Ejecuta el efecto de Parentesco
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Revela el aura completa del asesino
     * 2. Predice el próximo ataque con precisión
     * 3. Recupera 20 puntos de vida
     * 4. Aumenta la defensa base del superviviente en 10 puntos
     * 5. Activa el modo defensivo para bloquear el ataque predicho
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " revela el aura del asesino usando Parentesco.");
        
        caster.setVidaActual(caster.getVidaActual() + 20);
        caster.setDefensaBase(caster.getDefensaBase() + 10);
        caster.setDefendiendo(true);
        
        System.out.println("Predice el próximo ataque perfectamente. Recupera 20 HP y aumenta su defensa base.");
    }
}