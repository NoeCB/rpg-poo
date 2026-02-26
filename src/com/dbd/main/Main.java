package com.dbd.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.dbd.core.MotorTrial;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MotorTrial prueba = new MotorTrial();
        int opcion = 0;
        prueba.configurarPartida();

        do {
            System.out.println("\n--- BIENVENIDO A DEAD BY DAYLIGHT (TRIAL) ---");
            System.out.println("1. Mirar Personajes (Supervivientes)");
            System.out.println("2. Mirar Asesinos");
            System.out.println("3. Juego Automático (Simulación)");
            System.out.println("4. Juego Manual (Próximamente)");
            System.out.print("Selecciona una opción: ");

            opcion = errorNumero(sc);

            switch (opcion) {
                case 1:
                    System.out.println("Listando supervivientes...");
                    prueba.mostrarSupervivientes();

                    break;
                case 2:
                    System.out.println("Listando asesinos...");
                    prueba.mostrarAsesinos();
                    break;
                case 3:
                    System.out.println("Iniciando juego automático...");
                    // prueba.configurarPartida();
                    // prueba.iniciarJuego();
                    break;
                case 4:
                    // aqui iria el modo manual
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        } while (opcion != 4);

        sc.close();
    }

    public static int errorNumero(Scanner sc) {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: Introduce un número válido.");
            sc.nextLine();
            return 0;
        }
    }

}
