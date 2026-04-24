package com.dbd.entidades;

/**
 * Representa a Chucky, el muñeco diabólico (El Chico Bueno).
 * <p>
 * Poca vida para un asesino (140 HP) pero tremendamente resbaladizo (se puede
 * simular con defensa 10).
 *
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Chucky extends Personaje {

    public Chucky() {
        super("Chucky", 140, 140, 10);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " se ríe macabramente mientras correteá esquivando obstáculos.");
    }
}
