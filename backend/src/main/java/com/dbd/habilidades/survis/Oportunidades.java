package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Oportunidades
 * 
 * Perk de nivel 1 que permite al superviviente localizar los palés cercanos,
 * utilizando el entorno para aumentar su defensa de forma permanente.
 * 
 * Efectos:
 * - Localiza los palés cercanos en el mapa
 * - Aprovecha el terreno para mejorar posicionamiento
 * - Aumenta la defensa base del superviviente en 15 puntos de forma permanente
 * - El buff es permanente durante toda la partida
 * - Solo requiere 1 uso
 */
public class Oportunidades extends Perk {
    
    /**
     * Constructor de Oportunidades
     * 
     * Inicializa la habilidad con el nombre "Oportunidades" y 1 uso disponible.
     * El buff es permanente, por lo que solo necesita una activación.
     */
    public Oportunidades() {
        super("Oportunidades", 1); // 1 uso porque el buff es permanente
    }

    /**
     * Ejecuta el efecto de Oportunidades
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo no es utilizado en esta habilidad
     * 
     * Acciones realizadas:
     * 1. Localiza los palés cercanos en el mapa
     * 2. Analiza el terreno para encontrar oportunidades de defensa
     * 3. Aumenta la defensa base del superviviente en 15 puntos permanentemente
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " usa Oportunidades y localiza los palés cercanos.");
        
        // Le subimos la defensa base al personaje
        int nuevaDefensa = caster.getDefensaBase() + 15;
        caster.setDefensaBase(nuevaDefensa);
        
        System.out.println("Su defensa aumenta permanentemente a " + nuevaDefensa + ".");
    }
}