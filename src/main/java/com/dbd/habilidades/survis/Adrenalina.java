package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Adrenalina
 * 
 * Perk de nivel 1 que proporciona al superviviente un subidón de energía,
 * permitiéndole recuperarse rápidamente y mejorar su defensa de forma permanente.
 * 
 * Efectos:
 * - Recupera 60 puntos de vida instantáneamente
 * - Aumenta la defensa base de forma permanente en 20 puntos
 */
public class Adrenalina extends Perk {
    
    /**
     * Constructor de Adrenalina
     * 
     * Inicializa la habilidad con el nombre "Adrenalina" y nivel 1.
     */
    public Adrenalina() {
        super("Adrenalina", 1);
    }

    /**
     * Ejecuta el efecto de Adrenalina
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de activación de la habilidad
     * 2. Recupera 60 puntos de vida al lanzador
     * 3. Aumenta permanentemente la defensa base en 20 puntos
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡Se enciende el último motor! " + caster.getNombrePersonaje() + " recibe un subidón de Adrenalina.");
        
        // Cura masiva y subida de defensa
        caster.setVidaActual(caster.getVidaActual() + 60);
        caster.setDefensaBase(caster.getDefensaBase() + 20);
        
        System.out.println("Recupera 60 HP al instante y su defensa aumenta permanentemente. ¡Está listo para escapar!");
    }
}