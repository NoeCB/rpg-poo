package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Golpe Decisivo
 * 
 * Perk de nivel 1 que permite al superviviente asestar un golpe decisivo
 * con un cristal al asesino, requiere acertar un test de habilidad.
 * 
 * Efectos:
 * - Requiere acertar un test de habilidad
 * - Clava un cristal al asesino
 * - Inflige 40 puntos de daño
 * - Deja aturdido al asesino
 * - Activa el modo defensivo del superviviente
 * - Solo se puede usar una vez por partida
 */
public class GolpeDecisivo extends Perk {
    
    /**
     * Constructor de GolpeDecisivo
     * 
     * Inicializa la habilidad con el nombre "Golpe Decisivo" y 1 uso disponible.
     * Este perk es muy potente, por lo que solo se puede usar una vez por partida.
     */
    public GolpeDecisivo() {
        super("Golpe Decisivo", 1); // Solo se permite un uso por partida
    }

    /**
     * Ejecuta el efecto de Golpe Decisivo
     * 
     * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo el personaje que recibe el efecto (asesino)
     * 
     * Acciones realizadas:
     * 1. Acierta un test de habilidad
     * 2. Clava un cristal al asesino
     * 3. Inflige 40 puntos de daño al asesino
     * 4. Deja aturdido al asesino
     * 5. Activa el modo defensivo del superviviente para protegerse
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " acierta el test de habilidad y clava un cristal a " + objetivo.getNombrePersonaje() + "!");
        
        // Daño directo al Killer y el Superviviente se defiende
        objetivo.recibirDanio(40);
        caster.setDefendiendo(true);
        
        System.out.println(objetivo.getNombrePersonaje() + " queda aturdido por el golpe. " + caster.getNombrePersonaje() + " se pone en guardia.");
    }
}