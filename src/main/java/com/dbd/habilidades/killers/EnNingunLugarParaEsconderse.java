package com.dbd.habilidades.killers;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de asesino: En Ningún Lugar para Esconderse
 * 
 * Perk de nivel 3 que permite al asesino escudriñar la zona y descubrir
 * a los supervivientes escondidos, rompiéndoles la guardia.
 * 
 * Efectos:
 * - Escudriña la zona para localizar al objetivo
 * - Anula la postura defensiva si está activa
 * - Inflige 20 puntos de daño con un ataque directo y preciso
 * - Puede ser utilizado hasta 3 veces
 */
public class EnNingunLugarParaEsconderse extends Perk {
    
    /**
     * Constructor de EnNingunLugarParaEsconderse
     * 
     * Inicializa la habilidad con el nombre "En Ningún Lugar para Esconderse" y 3 usos disponibles.
     */
    public EnNingunLugarParaEsconderse() {
        super("En Ningún Lugar para Esconderse", 3);
    }

    /**
     * Ejecuta el efecto de En Ningún Lugar para Esconderse
     * 
     * @param caster el personaje que lanza la habilidad (asesino)
     * @param objetivo el personaje que recibe el efecto (superviviente)
     * 
     * Acciones realizadas:
     * 1. Muestra el mensaje de escudriñamiento de la zona
     * 2. Si el objetivo está defendiendo, anula su defensa
     * 3. Inflige 20 puntos de daño con un ataque directo
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " escudriña la zona con En Ningún Lugar para Esconderse.");
        
        if (objetivo.isDefendiendo()) {
            System.out.println("¡Descubre a " + objetivo.getNombrePersonaje() + " escondido y rompe su guardia!");
            objetivo.setDefendiendo(false); // Le quita el estado de defensa
        }
        
        System.out.println("Lanza un ataque directo y preciso.");
        objetivo.recibirDanio(20);
    }
}