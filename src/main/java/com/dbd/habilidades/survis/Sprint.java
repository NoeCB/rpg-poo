package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Sprint
 * * Perk de movilidad que permite al superviviente ganar una ventaja táctica
 * al tomar distancia, lo que se traduce en una recuperación de vitalidad 
 * y una postura defensiva inmediata.
 * * Efectos:
 * - Recupera 10 puntos de vida actual (simulando recuperación de estamina/aliento)
 * - Activa el modo defensivo automáticamente para mitigar el siguiente golpe
 * - Puede ser utilizado hasta 3 veces por partida
 */
public class Sprint extends Perk {
    
    /**
     * Constructor de Sprint
     * * Inicializa la habilidad con el nombre "Sprint" y un límite de 3 usos.
     */
    public Sprint() {
        super("Sprint", 3); // 3 usos por partida
    }

    /**
     * Ejecuta la acción de Sprint
     * * @param caster el personaje que utiliza la habilidad (superviviente)
     * @param objetivo el personaje rival (asesino), aunque no recibe daño directo en esta habilidad
     * * Acciones realizadas:
     * 1. Notifica el inicio de la carrera rápida.
     * 2. Incrementa la vida actual del caster en 10 unidades.
     * 3. Establece el estado de defensa en 'true'.
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        System.out.println(caster.getNombrePersonaje() + " activa Sprint y sale corriendo a toda velocidad.");
        
        // Lógica: Recuperación de vida y activación de guardia
        caster.setVidaActual(caster.getVidaActual() + 10);
        caster.setDefendiendo(true); 
        
        System.out.println("Ha tomado distancia. Recupera 10 HP y está preparado para esquivar el próximo ataque.");
    }
}