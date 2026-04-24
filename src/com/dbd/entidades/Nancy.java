package com.dbd.entidades;

/**
 * Representa a Nancy Wheeler, investigadora atrevida en combate contra el Otro Lado.
 * <p>
 * Vida de 105, defensa 12.
 *
 * @author Dead by Daylight RPG
 * @version 1.0
 */
public class Nancy extends Personaje {

    public Nancy() {
        super("Nancy Wheeler", 105, 105, 12);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " revisa los alrededores buscando activamente al asesino.");
    }
}
