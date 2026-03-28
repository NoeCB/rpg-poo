package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Barbacoa y Chile
 * 
 * Perk de nivel 3 que permite al asesino revelar las auras lejanas del objetivo,
 * ignorando su defensa y lanzando un ataque letal desde las sombras.
 * 
 * Efectos:
 * - Revela la aura del objetivo
 * - Anula la postura defensiva si está activa
 * - Inflige 25 puntos de daño
 * - Recupera 10 puntos de vida al asesino
 * - Puede ser utilizado hasta 3 veces
 */
public class BarbacoaYChile extends Perk {
    
    /**
     * Constructor de BarbacoaYChile
     * 
     * Inicializa la habilidad con el nombre "Barbacoa y Chile" y 3 usos disponibles.
     */
    public BarbacoaYChile() {
        super("Barbacoa y Chile", 3);
    }

    /**
     * Ejecuta el efecto de Barbacoa y Chile
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de revelación de aura
     * 2. Si el objetivo está defendiendo, anula su defensa
     * 3. Inflige 25 puntos de daño
     * 4. Recupera 10 puntos de vida al asesino
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " revela las auras lejanas con Barbacoa y Chile.");
        
        if (objetivo.isDefendiendo()) {
            System.out.println(objetivo.getNombrePersonaje() + " es descubierto. ¡Su guardia no sirve de nada!");
            objetivo.setDefendiendo(false);
        }
        
        System.out.println("Lanza un ataque letal desde las sombras.");
        objetivo.recibirDanio(25);
        
        caster.setVidaActual(caster.getVidaActual() + 10);
        System.out.println("El sacrificio inminente le cura 10 HP.");
    }
}