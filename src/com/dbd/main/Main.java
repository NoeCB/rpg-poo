package com.dbd.main;

import com.dbd.core.MotorTrial;
import static com.dbd.core.Util.*;
import java.util.Scanner;

public class Main {
  

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MotorTrial prueba = new MotorTrial();
        int opcion = 0;

        do {
            System.out.println(ROJO + "\n=============================================" + RESET);
            System.out.println(ROJO + "   --- BIENVENIDO A DEAD BY DAYLIGHT ---" + RESET);
            System.out.println(ROJO + "=============================================" + RESET);
            System.out.println(CYAN + "[1] Mirar Personajes (Supervivientes)" + RESET);
            System.out.println(ROJO + "[2] Mirar Asesinos (Killers)" + RESET);
            System.out.println(VERDE + "[3] Juego Automático (Simulación 3vs3)" + RESET);
            System.out.println(AMARILLO + "[4] Juego Manual" + RESET);
            System.out.println("[5] Salir del juego");
            System.out.print(AMARILLO + ">>> Selecciona una opción: " + RESET);

            opcion = errorNumero(sc);

            switch (opcion) {
                case 1:
                    prueba.mostrarSupervivientes();
                    break;
                case 2:
                    prueba.mostrarAsesinos();
                    break;
                case 3:
                    System.out.println(VERDE + "\n--- INICIANDO MODO AUTOMÁTICO ---" + RESET);
                    prueba.configurarPartida();
                    prueba.iniciarAleatorio();
                    prueba.iniciarJuego();
                    break;
                case 4:
                    System.out.println(AMARILLO + "\n--- INICIANDO MODO MANUAL ---" + RESET);
                    prueba.configurarPartida();
                    prueba.iniciarManual();
                    prueba.iniciarJuegoManual();
                    break;
                case 5:
                    System.out.println(ROJO + "\nCerrando el juego... ¡La Entidad te espera en la niebla!" + RESET);
                    break;
                default:
                    System.out.println(ROJO + " Opción no válida. Por favor, elige un número del 1 al 5." + RESET);
            }
        } while (opcion != 5);

        sc.close();
    }

   
}