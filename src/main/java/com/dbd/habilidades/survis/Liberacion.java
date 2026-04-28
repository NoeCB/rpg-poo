package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Liberación
 * 
 * Perk de nivel 1 que permite al superviviente descolgarse a sí mismo cuando está atrapado,
 * recuperándose y adoptar una postura defensiva para huir del asesino.
 * 
 * Efectos:
 * - Se descuelga a sí mismo de trampas o restricciones
 * - Recupera 30 puntos de vida
 * - Adopta una postura defensiva perfecta
 * - Bloquea el siguiente ataque del asesino
 * - Puede ser utilizado 1 sola vez
 */
public class Liberacion extends Perk {
    
    /**
     * Constructor de Liberacion
     * 
     * Inicializa la habilidad con el nombre "Liberación" y 1 uso disponible.
     * Esta habilidad es esencial para escapar de situaciones críticas.
     */
    public Liberacion() {
        super("Liberación", 1);
    }

    /**
     * Ejecuta el efecto de Liberación
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Se descuelga a sí mismo de cualquier restricción o trampa
     * 2. Recupera 30 puntos de vida al liberarse
     * 3. Adopta una postura defensiva perfecta
     * 4. Activa el modo defensivo para bloquear el siguiente ataque
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se descuelga a sí mismo gracias a Liberación.");
        
        caster.setVidaActual(caster.getVidaActual() + 30);
        caster.setDefendiendo(true); // Se asegura de bloquear el siguiente ataque
        
        System.out.println("Recupera 30 HP y adopta una postura defensiva perfecta para huir del asesino.");
    }
}