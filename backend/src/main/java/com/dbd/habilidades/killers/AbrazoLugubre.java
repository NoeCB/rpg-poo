package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Abrazo Lúgubre
 * 
 * Perk de nivel 1 que permite al asesino drenar la esperanza del objetivo,
 * causando daño mientras se cura a sí mismo.
 * 
 * Efecto:
 * - Inflige 20 puntos de daño al objetivo
 * - Recupera 30 puntos de vida al asesino
 */
public class AbrazoLugubre extends Perk {
    
    /**
     * Constructor de AbrazoLugubre
     * 
     * Inicializa la habilidad con el nombre "Abrazo Lúgubre" y nivel 1.
     */
    public AbrazoLugubre() {
        super("Abrazo Lúgubre", 1);
    }

    /**
     * Ejecuta el efecto de Abrazo Lúgubre
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto de la habilidad
     * 
     * Acciones realizadas:
     * 1. Muestra mensaje de ejecución de la habilidad
     * 2. Inflige 20 puntos de daño al objetivo
     * 3. Recupera 30 puntos de vida al asesino como recompensa
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " cierra sus garras con Abrazo Lúgubre.");
        
        System.out.println("Drena la esperanza de " + objetivo.getNombrePersonaje() + ", robándole vida.");
        objetivo.recibirDanio(20);
        
        caster.setVidaActual(caster.getVidaActual() + 30);
        System.out.println("La Entidad complace al asesino, curándole 30 HP.");
    }
}