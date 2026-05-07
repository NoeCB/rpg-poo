package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Maleficio: NOED (Nobody Ever Escapes Death)
 * 
 * Perk de nivel 1 que se activa cuando todos los motores han sido reparados.
 * Proporciona un golpe crítico devastador y aumenta permanentemente la defensa del asesino.
 * 
 * Efectos:
 * - Se activa cuando todos los generadores han sido completados
 * - Deja a todos los supervivientes EXPUESTOS
 * - Aumenta la velocidad de movimiento del asesino
 * - Inflige 55 puntos de daño (golpe crítico masivo)
 * - Aumenta la defensa base del asesino en 20 puntos
 */
public class NadieEscapaDeLaMuerte extends Perk {
    
    /**
     * Constructor de NadieEscapaDeLaMuerte
     * 
     * Inicializa la habilidad con el nombre "Maleficio: NOED" y 1 uso disponible.
     * Aunque solo tiene 1 uso, es extremadamente efectivo cuando se activa.
     */
    public NadieEscapaDeLaMuerte() {
        super("Maleficio: NOED", 1); // Solo 1 uso, pero letal
    }

    /**
     * Ejecuta el efecto de Maleficio: NOED
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Se activa al reparar el último motor
     * 2. Aumenta la velocidad de movimiento del asesino
     * 3. Deja a todos los supervivientes EXPUESTOS
     * 4. Aumenta la defensa base del asesino en 20 puntos
     * 5. Inflige 55 puntos de daño (golpe crítico devastador)
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡Se han reparado todos los motores! " + caster.getNombrePersonaje() + " activa NOED.");
        System.out.println("¡Aumenta su velocidad de movimiento y todos quedan EXPUESTOS!");
        
        // Ataque crítico masivo y aumento de defensa/velocidad
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        objetivo.recibirDanio(55); 
        
        System.out.println(objetivo.getNombrePersonaje() + " recibe un golpe crítico devastador. ¡La muerte acecha!");
    }
}