package com.dbd.estados;

import com.dbd.entidades.Personaje;

/**
 * Representa un "Escudo", que brinda un bloqueo absoluto del siguiente ataque
 * recibido si el componente protector sigue activo durante el turno.
 *
 * Esta es una de las 3 mejoras especiales al sistema que permite interacciones
 * más avanzadas en combate. El Escudo puede consumirse pero bloquea todo tipo de daño.
 * 
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Escudo extends Estado {

    public Escudo(int turnosRestantes) {
        super("Escudo", turnosRestantes);
    }

    @Override
    public void aplicarEfecto(Personaje objetivo) {
        // El Escudo da inmunidad durante la duración fijando la defensa alta 
        // y estableciendo una mitigación superior momentánea (Defensa extrema)
        System.out.println("El Escudo sagrado protege a " + objetivo.getNombrePersonaje() + " de daños letales.");
        
        // Pone al personaje automáticamente en postura defensiva cada turno mientras dure
        objetivo.setDefendiendo(true);
        
        this.turnosRestantes--;
        if (turnosRestantes <= 0) {
            System.out.println("El Escudo de " + objetivo.getNombrePersonaje() + " se ha roto por completo.");
        }
    }
}
