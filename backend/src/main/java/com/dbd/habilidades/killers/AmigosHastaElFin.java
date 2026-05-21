package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Amigos hasta el Fin
 * 
 * Perk de nivel 2 que permite al asesino obsesionarse con su objetivo,
 * dejándolo expuesto y vulnerable a un golpe crítico devastador.
 * 
 * Efectos:
 * - Deja al superviviente en estado EXPUESTO
 * - Inflige 45 puntos de daño como golpe crítico masivo
 * - Puede ser utilizado hasta 2 veces
 */
public class AmigosHastaElFin extends Perk {
    
    /**
     * Constructor de AmigosHastaElFin
     * 
     * Inicializa la habilidad con el nombre "Amigos hasta el Fin" y 2 usos disponibles.
     */
    public AmigosHastaElFin() {
        super("Amigos hasta el Fin", 2);
    }

    /**
     * Ejecuta el efecto de Amigos hasta el Fin
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de obsesión del asesino con su objetivo
     * 2. Expone al superviviente, dejándolo vulnerable
     * 3. Inflige 45 puntos de daño como golpe crítico
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " se obsesiona con su objetivo usando Amigos hasta el Fin.");
        
        System.out.println("¡" + objetivo.getNombrePersonaje() + " queda EXPUESTO y recibe un golpe crítico masivo!");
        objetivo.recibirDanio(45); // Daño muy alto simulando el estado Expuesto
    }
}