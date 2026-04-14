package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Fajador (Dead Hard)
 * 
 * Perk de nivel 2 que permite al superviviente activar una explosión de resistencia
 * cuando está gravemente herido, recuperándose instantáneamente.
 * 
 * Efectos:
 * - Solo funciona cuando el superviviente está por debajo del 50% de vida
 * - Al activarse, recupera 40 puntos de vida al instante
 * - Gana estado de Resistencia
 * - No se puede usar si el superviviente tiene más del 50% de vida
 * - Puede ser utilizado hasta 2 veces
 */
public class Fajador extends Perk {
    
    /**
     * Constructor de Fajador
     * 
     * Inicializa la habilidad con el nombre "Fajador" y 2 usos disponibles.
     * Esta es una habilidad muy potente, por lo que consume ambos usos rápidamente.
     */
    public Fajador() {
        super("Fajador", 2); // Solo 2 usos, es muy fuerte
    }

    /**
     * Ejecuta el efecto de Fajador
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas (si está por debajo del 50% de vida):
     * 1. Verifica si el superviviente está gravemente herido (≤ 50% de vida)
     * 2. Si cumple la condición, activa la explosión de resistencia
     * 3. Recupera 40 puntos de vida al instante
     * 4. Aplica el estado de Resistencia al superviviente
     * 
     * Si no cumple la condición, solo muestra un mensaje de fallo.
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        // Calculamos si está herido
        double porcentajeVida = (double) caster.getVidaActual() / caster.getVidaMax();
        
        if (porcentajeVida <= 0.50) {
            System.out.println(caster.getNombrePersonaje() + " aprieta los dientes y activa Fajador (Dead Hard)!");
            caster.setVidaActual(caster.getVidaActual() + 40);
            System.out.println("Gana Resistencia y recupera 40 HP al instante.");
        } else {
            System.out.println(caster.getNombrePersonaje() + " intenta usar Fajador, pero está demasiado sano para activarlo.");
        }
    }
}