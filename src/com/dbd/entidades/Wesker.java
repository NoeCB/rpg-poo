package com.dbd.entidades;

/**
 * Representa a Albert Wesker (La Mente Maestra/El Cerebro).
 * <p>
 * Alta vida (150) y gran defensa (20).
 *
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Wesker extends Personaje {

    public Wesker() {
        super("Albert Wesker", 150, 150, 20);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " se acomoda las gafas oscuras y dice: \"Siete minutos... Es todo el tiempo que puedo dedicarte.\"");
    }
}
