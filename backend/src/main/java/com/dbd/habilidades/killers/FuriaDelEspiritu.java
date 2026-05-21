package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Furia del Espíritu
 * 
 * Perk de nivel 2 que permite al asesino desatar una furia espiritual devastadora,
 * destruyendo completamente la protección del superviviente.
 * 
 * Efectos:
 * - Destruye la defensa del objetivo por completo
 * - Reduce la defensa base del superviviente en 20 puntos de forma permanente
 * - Inflige 30 puntos de daño
 * - Puede ser utilizado hasta 2 veces
 */
public class FuriaDelEspiritu extends Perk {
    
    /**
     * Constructor de FuriaDelEspiritu
     * 
     * Inicializa la habilidad con el nombre "Furia del Espíritu" y 2 usos disponibles.
     */
    public FuriaDelEspiritu() {
        super("Furia del Espíritu", 2);
    }

    /**
     * Ejecuta el efecto de Furia del Espíritu
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de desatamiento de la furia espiritual
     * 2. Reduce la defensa base del objetivo en 20 puntos (mínimo 0)
     * 3. Inflige 30 puntos de daño
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " desata la Furia del Espíritu.");
        
        // Destruye la defensa (los recursos) y ataca
        int defensaPerdida = Math.max(0, objetivo.getDefensaBase() - 20);
        objetivo.setDefensaBase(defensaPerdida);
        
        System.out.println("¡Destruye la protección de " + objetivo.getNombrePersonaje() + " por completo!");
        objetivo.recibirDanio(30);
    }
}