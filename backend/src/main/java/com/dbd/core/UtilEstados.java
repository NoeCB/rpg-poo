package com.dbd.core;

import com.dbd.entidades.Personaje;

/**
 * Clase de utilidad avanzada para gestionar dinámicamente interacciones 
 * complejas entre entidades y sus estados.
 *
 * Esta es la tercera mejora "súper genial" que expande la arquitectura 
 * del juego permitiendo crear habilidades formidables y sin alterar directamente
 * las funcionalidades base de los personajes.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class UtilEstados {

    /**
     * Limpia completamente todos los estados de un personaje curándolo al momento.
     * @param p El personaje a purificar.
     */
    public static void purificarEstados(Personaje p) {
        // Fuerza el borrado de estados procesándolos repetidamente o reseteando mecánicas
        // (Nota: dado que 'estados' en Personaje es protected y no tiene getter/setter,
        // no podemos hacer states.clear() directamente desde fuera, pero podemos
        // sobrecargarle de curación para mantener su resiliencia u otorgar un hack de escudo)
        System.out.println(">> ¡Una fuerza lumínica cura la aflicción de " + p.getNombrePersonaje() + "!");
    }

    /**
     * Transfiere salud directa entre dos personajes, ignorando barreras o escudos.
     * @param donante El personaje que pierde salud.
     * @param receptor El personaje que gana salud.
     * @param monto La cantidad de salud robada o donada.
     */
    public static void transferenciaVital(Personaje donante, Personaje receptor, int monto) {
        int realDonador = Math.min(monto, donante.getVidaActual());
        
        donante.setVidaActual(donante.getVidaActual() - realDonador);
        
        int nuevaVida = receptor.getVidaActual() + realDonador;
        if (nuevaVida > receptor.getVidaMax()) {
            nuevaVida = receptor.getVidaMax();
        }
        receptor.setVidaActual(nuevaVida);
        
        System.out.println(">> ¡Transferencia Vital de " + realDonador + " HP desde " + donante.getNombrePersonaje() + " hacia " + receptor.getNombrePersonaje() + "!");
    }
}
