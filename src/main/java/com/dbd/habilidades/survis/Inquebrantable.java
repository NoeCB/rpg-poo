package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Inquebrantable
 * 
 * Perk de nivel 1 que permite al superviviente negarse a caer cuando está
 * en estado crítico, recuperándose instantáneamente.
 * 
 * Efectos:
 * - Solo funciona cuando el superviviente está por debajo del 25% de vida
 * - Al activarse, se recupera del estado crítico
 * - Recupera 50 puntos de vida al instante
 * - No se puede usar si el superviviente tiene más del 25% de vida
 * - Puede ser utilizado 1 sola vez
 */
public class Inquebrantable extends Perk {
    
    /**
     * Constructor de Inquebrantable
     * 
     * Inicializa la habilidad con el nombre "Inquebrantable" y 1 uso disponible.
     * Esta habilidad es crucial para la supervivencia en momentos críticos.
     */
    public Inquebrantable() {
        super("Inquebrantable", 1);
    }

    /**
     * Ejecuta el efecto de Inquebrantable
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas (si está en estado crítico ≤ 25% de vida):
     * 1. Verifica si el superviviente está en estado crítico (≤ 25% de vida)
     * 2. Si cumple la condición, se niega a caer
     * 3. Se recupera del estado crítico
     * 4. Recupera 50 puntos de vida al instante
     * 
     * Si no cumple la condición, solo muestra un mensaje de fallo.
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        double porcentajeVida = (double) caster.getVidaActual() / caster.getVidaMax();
        
        if (porcentajeVida <= 0.25) {
            System.out.println(caster.getNombrePersonaje() + " se niega a caer y activa Inquebrantable.");
            caster.setVidaActual(caster.getVidaActual() + 50);
            System.out.println("Se recupera del estado crítico ganando 50 HP.");
        } else {
            System.out.println(caster.getNombrePersonaje() + " intenta usar Inquebrantable, pero aún no está en estado crítico.");
        }
    }
}