package com.dbd.habilidades.survis;

import com.dbd.entidades.Personaje;
import com.dbd.habilidades.Perk;

/**
 * Habilidad de superviviente: Resiliencia
 * * Perk de supervivencia que aprovecha el estado crítico del personaje para 
 * potenciar sus capacidades defensivas y ofensivas.
 * * Efectos:
 * - Solo se activa si la vida actual es igual o inferior al 50%
 * - Inflige 20 puntos de daño como contraataque desesperado
 * - Activa el modo defensivo automáticamente
 * - Puede ser utilizado hasta 3 veces
 */
public class Resiliencia extends Perk {
    
    /**
     * Constructor de Resiliencia
     * * Inicializa la habilidad con el nombre "Resiliencia" y 3 usos disponibles.
     */
    public Resiliencia() {
        super("Resiliencia", 3); // Puede usarse más veces al ser pasiva/activa
    }

    /**
     * Ejecuta el efecto de Resiliencia basándose en el estado de salud del caster.
     * * @param caster el personaje que lanza la habilidad (superviviente)
     * @param objetivo el personaje que recibe el efecto (asesino)
     * * Lógica de activación:
     * 1. Calcula el porcentaje de vida restante del superviviente.
     * 2. Si es <= 50%, ejecuta un ataque de 20 de daño y activa la defensa.
     * 3. Si es > 50%, la habilidad se intenta usar pero no genera efectos prácticos.
     */
    @Override
    public void lanzar(Personaje caster, Personaje objetivo) {
        double porcentajeVida = (double) caster.getVidaActual() / caster.getVidaMax();
        
        if (porcentajeVida <= 0.50) {
            System.out.println(caster.getNombrePersonaje() + " canaliza su dolor con Resiliencia.");
            System.out.println("¡Al estar herido, actúa más rápido y lanza un ataque desesperado!");
            
            // Hace un daño fijo y se prepara para el golpe
            objetivo.recibirDanio(20);
            caster.setDefendiendo(true);
        } else {
            System.out.println(caster.getNombrePersonaje() + " está demasiado sano para aprovechar Resiliencia.");
        }
    }
}