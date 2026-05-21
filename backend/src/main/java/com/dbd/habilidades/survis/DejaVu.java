package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Déjà Vu
 * 
 * Perk de nivel 1 que permite al superviviente analizar el entorno y trazar un plan perfecto,
 * aprovechando el conocimiento del terreno para defenderse.
 * 
 * Efectos:
 * - Analiza el entorno para obtener ventaja táctica
 * - Aumenta la defensa base del superviviente en 15 puntos de forma permanente
 * - Permite un contraataque usando el entorno
 * - Inflige 15 puntos de daño al asesino
 */
public class DejaVu extends Perk {
    
    /**
     * Constructor de DejaVu
     * 
     * Inicializa la habilidad con el nombre "Déjà Vu" y 1 uso disponible.
     * El efecto es un buff permanente durante el combate.
     */
    public DejaVu() {
        super("Déjà Vu", 1); // 1 uso, buff permanente
    }

    /**
     * Ejecuta el efecto de Déjà Vu
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo el personaje que recibe el efecto (asesino)
     * 
     * Acciones realizadas:
     * 1. Analiza el entorno para obtener un plan perfecto
     * 2. Aumenta la defensa base del superviviente en 15 puntos permanentemente
     * 3. Aprovecha el posicionamiento para contraatacar
     * 4. Inflige 15 puntos de daño al asesino
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " analiza el entorno con Déjà Vu y traza un plan perfecto.");
        
        // Sube su defensa base y hace un ataque táctico
        caster.setDefensaBase(caster.getDefensaBase() + 15);
        System.out.println("Su defensa aumenta permanentemente. ¡Conoce el terreno!");
        
        System.out.println("Aprovecha su posicionamiento para golpear a " + objetivo.getNombrePersonaje() + " con el entorno.");
        objetivo.recibirDanio(15);
    }
}