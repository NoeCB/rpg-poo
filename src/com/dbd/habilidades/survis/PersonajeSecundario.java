package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Personaje Secundario
 * 
 * Perk de nivel 2 que permite al superviviente salir disparado a una velocidad extraordinaria,
 * cegando y golpeando al asesino en un instante.
 * 
 * Efectos:
 * - Aumenta la velocidad de movimiento al 150%
 * - Ciega al asesino temporalmente
 * - Inflige 30 puntos de daño con un golpe rápido
 * - Activa el modo defensivo automáticamente
 * - Puede ser utilizado hasta 2 veces
 */
public class PersonajeSecundario extends Perk {
    
    /**
     * Constructor de PersonajeSecundario
     * 
     * Inicializa la habilidad con el nombre "Personaje Secundario" y 2 usos disponibles.
     */
    public PersonajeSecundario() {
        super("Personaje Secundario", 2); 
    }

    /**
     * Ejecuta el efecto de Personaje Secundario
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo el personaje que recibe el efecto (asesino)
     * 
     * Acciones realizadas:
     * 1. Sale disparado a velocidad del 150%
     * 2. Ejecuta un ataque rápido que ciega al asesino
     * 3. Inflige 30 puntos de daño
     * 4. Activa el modo defensivo automáticamente tras el ataque
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println("¡" + caster.getNombrePersonaje() + " sale disparado al 150% de velocidad con Personaje Secundario!");
        
        // Daño moderado-alto y defensa automática
        System.out.println("¡Ciega y golpea a " + objetivo.getNombrePersonaje() + " en un abrir y cerrar de ojos!");
        objetivo.recibirDanio(30);
        caster.setDefendiendo(true);
    }
}