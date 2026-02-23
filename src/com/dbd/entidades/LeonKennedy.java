package com.dbd.entidades;

public class LeonKennedy extends Personaje {

    // Lo dejamos sin parámetros para que MotorTrial pueda hacer "new LeonKennedy()" sin errores
    public LeonKennedy() {
        // Le pasamos los 5 datos obligatorios a la clase padre (Personaje):
        // Nombre, vidaActual, vidaMax, defensaBase, puntosSangre
        super("Leon Kennedy", 100, 100, 15, 0);
    }

    @Override
    public void accion() {
        System.out.println(this.nombrePersonaje + " empuña su linterna y se prepara para el combate.");
    }
}