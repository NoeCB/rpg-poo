package com.dbd.main;

import com.dbd.core.MotorTrial;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // Definimos los colores para usarlos en los textos
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MotorTrial prueba = new MotorTrial();
        int opcion = 0;

        do {
            System.out.println(ROJO + "\n=============================================" + RESET);
            System.out.println(AMARILLO + "   --- BIENVENIDO A DEAD BY DAYLIGHT ---" + RESET);
            System.out.println(ROJO + "=============================================" + RESET);
            System.out.println(CYAN + "[1] Mirar Personajes (Supervivientes)" + RESET);
            System.out.println(ROJO + "[2] Mirar Asesinos (Killers)" + RESET);
            System.out.println(VERDE + "[3] Juego Automático (Simulación 3vs3)" + RESET);
            System.out.println(AMARILLO + "[4] Juego Manual (Próximamente)" + RESET);
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
                    prueba.iniciar();
                    prueba.iniciarJuego();
                    break;
                case 4:
                    System.out.println(AMARILLO + "\n--- INICIANDO MODO MANUAL ---" + RESET);
                    System.out.println(" ¡Modo en construcción! Trabajando en ello...");
                    break;
                case 5:
                    System.out.println(ROJO + "\nCerrando el juego... ¡La Entidad te espera en la niebla!" + RESET);
                    break;
                default:
                    if (opcion != 0) {
                        System.out
                                .println(ROJO + " Opción no válida. Por favor, elige un número del 1 al 5." + RESET);
                    }
            }
        } while (opcion != 5);

        sc.close();
    }

    public static int errorNumero(Scanner sc) {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(
                    "\u001B[31m" + " Error: ¡Debes introducir un número, no letras o símbolos!" + "\u001B[0m");
            sc.nextLine();
            return 0;
        }
    }
}