package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Intervención Corrupta
 * 
 * Perk de nivel 1 que invoca al Ente para bloquear las rutas y mejorar permanentemente
 * la defensa del asesino. Este efecto es pasivo y permanente en el combate.
 * 
 * Efectos:
 * - Invoca al Ente (La Entidad)
 * - Bloquea las rutas de escape del superviviente
 * - Aumenta la defensa base del asesino en 25 puntos de forma permanente
 */
public class IntervencionCorrupta extends Perk {
    
    /**
     * Constructor de IntervencionCorrupta
     * 
     * Inicializa la habilidad con el nombre "Intervención Corrupta" y 1 uso disponible.
     * El efecto es pasivo/permanente, por lo que solo se activa una vez.
     */
    public IntervencionCorrupta() {
        super("Intervención Corrupta", 1); // Efecto pasivo/permanente, solo necesita 1 uso
    }

    /**
     * Ejecuta el efecto de Intervención Corrupta
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de invocación del Ente
     * 2. Aumenta la defensa base del asesino en 25 puntos permanentemente
     * 3. Bloquea las rutas de escape con la niebla
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " invoca al Ente con Intervención Corrupta.");
        
        caster.setDefensaBase(caster.getDefensaBase() + 25);
        System.out.println("La niebla bloquea las rutas. La defensa del asesino aumenta en 25 puntos permanentemente.");
    }
}