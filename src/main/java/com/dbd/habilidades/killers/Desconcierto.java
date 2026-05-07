package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Desconcierto
 * 
 * Perk de nivel 2 que permite al asesino bloquear las rutas de escape,
 * dejando al superviviente sin recursos defensivos.
 * 
 * Efectos:
 * - Bloquea las rutas de escape del objetivo
 * - Reduce la defensa base del superviviente en 10 puntos de forma permanente
 * - Inflige 15 puntos de daño
 * - Puede ser utilizado hasta 2 veces
 */
public class Desconcierto extends Perk {
    
    /**
     * Constructor de Desconcierto
     * 
     * Inicializa la habilidad con el nombre "Desconcierto" y 2 usos disponibles.
     */
    public Desconcierto() {
        super("Desconcierto", 2);
    }

    /**
     * Ejecuta el efecto de Desconcierto
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de bloqueo de rutas de escape
     * 2. Reduce la defensa base del objetivo en 10 puntos (mínimo 0)
     * 3. Inflige 15 puntos de daño al objetivo
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " bloquea las rutas de escape con Desconcierto.");
        
        // Reducimos la defensa del superviviente (asegurando que no baje de 0)
        int defensaPerdida = Math.max(0, objetivo.getDefensaBase() - 10);
        objetivo.setDefensaBase(defensaPerdida);
        
        System.out.println("¡" + objetivo.getNombrePersonaje() + " se queda sin recursos! Su defensa base se reduce permanentemente.");
        objetivo.recibirDanio(15);
    }
}