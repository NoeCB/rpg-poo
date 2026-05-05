package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: Maleficio: Juguete
 * 
 * Perk de nivel 2 que permite al asesino maldecir a su víctima, haciéndola perder
 * la noción del peligro e imposible protegerse.
 * 
 * Efectos:
 * - Maldice al superviviente
 * - Lo pone en estado INCONSCIENTE
 * - Reduce la defensa base del superviviente en 15 puntos de forma permanente
 * - Inflige 15 puntos de daño
 * - Puede ser utilizado hasta 2 veces
 */
public class MaleficioJuguete extends Perk {
    
    /**
     * Constructor de MaleficioJuguete
     * 
     * Inicializa la habilidad con el nombre "Maleficio: Juguete" y 2 usos disponibles.
     */
    public MaleficioJuguete() {
        super("Maleficio: Juguete", 2);
    }

    /**
     * Ejecuta el efecto de Maleficio: Juguete
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de maldición
     * 2. Reduce la defensa base del objetivo en 15 puntos (mínimo 0)
     * 3. Aplica el estado INCONSCIENTE al objetivo
     * 4. Inflige 15 puntos de daño
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " maldice a su víctima con Maleficio: Juguete.");
        
        // El superviviente pierde la noción del peligro (pierde defensa)
        int defensaPerdida = Math.max(0, objetivo.getDefensaBase() - 15);
        objetivo.setDefensaBase(defensaPerdida);
        
        System.out.println("¡" + objetivo.getNombrePersonaje() + " sufre el estado INCONSCIENTE! Su defensa cae en picado.");
        objetivo.recibirDanio(15);
    }
}