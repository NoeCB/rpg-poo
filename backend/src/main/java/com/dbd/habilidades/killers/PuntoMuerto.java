package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Punto Muerto
 * 
 * Perk de nivel 1 que permite al asesino paralizar el avance del rival,
 * sellando el campo de batalla e incrementando su defensa.
 * 
 * Efectos:
 * - Paraliza el avance del superviviente
 * - Sella el campo de batalla con intervención de la Entidad
 * - Aumenta la defensa base del asesino en 20 puntos de forma permanente
 * - Activa el modo defensivo del asesino
 */
public class PuntoMuerto extends Perk {
    
    /**
     * Constructor de PuntoMuerto
     * 
     * Inicializa la habilidad con el nombre "Punto Muerto" y 1 uso disponible.
     */
    public PuntoMuerto() {
        super("Punto Muerto", 1);
    }

    /**
     * Ejecuta el efecto de Punto Muerto
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Paraliza el avance del rival con intervención de la Entidad
     * 2. Aumenta la defensa base del asesino en 20 puntos permanentemente
     * 3. Activa el modo defensivo del asesino
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " paraliza el avance del rival con Punto Muerto.");
        
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        caster.setDefendiendo(true);
        
        System.out.println("La Entidad sella el campo de batalla. La defensa aumenta y bloquea el próximo ataque.");
    }
}