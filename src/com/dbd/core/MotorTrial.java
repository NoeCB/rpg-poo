package com.dbd.core;

import com.dbd.entidades.Personaje;
import java.util.ArrayList;

public class MotorTrial {
    // Listas preparadas para usar en los siguientes dias de desarrollo
    ArrayList<Personaje> supervivientes = new ArrayList<>();
    ArrayList<Personaje> asesinos = new ArrayList<>();

    public void iniciar() {
        System.out.println("Bienvenido al juego de Dead by Daylight");
        System.out.println("El juego consiste en un RPG por turnos con tres supervivientes y tres killers");
        System.out.println("Primero elige los supervivientes ");
        System.out.println("--JUEGO TERMINADO--");
    }
}
