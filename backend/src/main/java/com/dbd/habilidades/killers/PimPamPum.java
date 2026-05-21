package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Pim, pam, pum
 * 
 * Perk de nivel 3 que permite al asesino asestar un golpe devastador
 * que rompe las defensas del superviviente.
 * 
 * Efectos:
 * - Golpe devastador que ignora parcialmente la defensa
 * - Inflige 30 puntos de daño
 * - El asesino se prepara en postura defensiva tras el ataque
 * - Puede ser utilizado hasta 3 veces
 */
public class PimPamPum extends Perk {
    
    /**
     * Constructor de PimPamPum
     * 
     * Inicializa la habilidad con el nombre "Pim, pam, pum" y 3 usos disponibles.
     */
    public PimPamPum() {
        super("Pim, pam, pum", 3);
    }

    /**
     * Ejecuta el efecto de Pim, pam, pum
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje del uso de la habilidad
     * 2. Asesta un golpe devastador al objetivo
     * 3. Inflige 30 puntos de daño
     * 4. Activa el modo defensivo del asesino tras el gran esfuerzo
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " usa Pim, pam, pum.");
        
        System.out.println("¡Asesta un golpe devastador que rompe las defensas de " + objetivo.getNombrePersonaje() + "!");
        objetivo.recibirDanio(30);
        caster.setDefendiendo(true); // Se prepara tras el gran golpe
    }
}