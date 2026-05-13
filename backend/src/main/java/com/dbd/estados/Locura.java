package com.dbd.estados;

import com.dbd.entidades.Personaje;

/**
 * Representa el estado de "Locura", que daña progresivamente la mente del personaje,
 * restando salud pasivamente y erosionando su defensa base a lo largo del tiempo.
 *
 * Esta es una de las 3 mejoras al código general introducidas en esta actualización.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Locura extends Estado {

    public Locura(int turnosRestantes) {
        super("Locura", turnosRestantes);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        System.out.println("La " + this.nombre + " corrompe la mente de " + objetivo.getNombrePersonaje() + "...");
        
        // Efecto 1: Resta 5 puntos de vida
        int nuevaVida = objetivo.getVidaActual() - 5;
        if (nuevaVida < 0) nuevaVida = 0;
        objetivo.setVidaActual(nuevaVida);
        
        // Efecto 2: Degrada la defensa base en 2 puntos cada turno (mínimo 0)
        int nuevaDefensa = objetivo.getDefensaBase() - 2;
        if (nuevaDefensa < 0) nuevaDefensa = 0;
        objetivo.setDefensaBase(nuevaDefensa);

        System.out.println(objetivo.getNombrePersonaje() + " pierde 5 de salud y 2 de defensa base debido a la Locura.");
        System.out.println("Salud actual: " + objetivo.getVidaActual() + " Defensa actual: " + objetivo.getDefensaBase());
        
        this.turnosRestantes--;
    }
}
