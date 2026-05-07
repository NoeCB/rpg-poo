package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Perseguidor Letal
 * 
 * Perk de nivel 1 que permite al asesino leer las auras del objetivo,
 * conociendo exactamente dónde golpear para infligir un daño preciso y letal.
 * 
 * Efectos:
 * - Lee las auras del objetivo para determinar el punto débil
 * - Inflige 25 puntos de daño con un ataque preciso y letal
 * - Recupera 15 puntos de vida al asesino
 */
public class PerseguidorLetal extends Perk {
    
    /**
     * Constructor de PerseguidorLetal
     * 
     * Inicializa la habilidad con el nombre "Perseguidor Letal" y 1 uso disponible.
     */
    public PerseguidorLetal() {
        super("Perseguidor Letal", 1);
    }

    /**
     * Ejecuta el efecto de Perseguidor Letal
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Lee las auras del objetivo para localizar su punto débil
     * 2. Inflige 25 puntos de daño con un ataque preciso y letal
     * 3. Recupera 15 puntos de vida al asesino gracias a la adrenalina de la caza
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " lee las auras con Perseguidor Letal.");
        
        System.out.println("Conoce exactamente dónde golpear. ¡Lanza un ataque preciso y letal!");
        objetivo.recibirDanio(25);
        
        caster.setVidaActual(caster.getVidaActual() + 15);
        System.out.println("La adrenalina de la caza temprana le cura 15 HP.");
    }
}