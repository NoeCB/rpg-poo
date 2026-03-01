package com.dbd.main;

import com.dbd.core.MotorTrial;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MotorTrial prueba = new MotorTrial();
        int opcion = 0;

        do {
            System.out.println("\n=============================================");
            System.out.println("   --- BIENVENIDO A DEAD BY DAYLIGHT ---");
            System.out.println("=============================================");
            System.out.println("[1] Mirar Personajes (Supervivientes)");
            System.out.println("[2] Mirar Asesinos (Killers)");
            System.out.println("[3] Juego Automático (Simulación 3vs3)");
            System.out.println("[4] Juego Manual (Próximamente)");
            System.out.println("[5] Salir del juego");
            System.out.print(">>> Selecciona una opción: ");

            opcion = errorNumero(sc);

            switch (opcion) {
                case 1:
                    System.out.println("\n--- LISTA DE SUPERVIVIENTES ---");
                 
                    break;
                case 2:
                    System.out.println("\n--- LISTA DE ASESINOS ---");
                    prueba.mostrarAsesinos();
                    break;
                case 3:
                    System.out.println("\n--- INICIANDO MODO AUTOMÁTICO ---");
                    // Aquí es donde DEBE ir el inicio del juego, dentro de la opción 3
                    prueba.iniciar();       
                    prueba.iniciarJuego();
                    break;
                case 4:
                    System.out.println("\n--- INICIANDO MODO MANUAL ---");
                    System.out.println("¡Modo en construcción! Trabajando en ello...");
                    break;
                case 5:
                    System.out.println("\nCerrando el juego... ¡La Entidad te espera en la niebla!");
                    break;
                default:
                    if (opcion != 0) { // Si es 0, el errorNumero ya soltó el mensaje de error
                        System.out.println("Opción no válida. Por favor, elige un número del 1 al 5.");
                    }
            }
        } while (opcion != 5);

        sc.close();
    }

    public static int errorNumero(Scanner sc) {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: ¡Debes introducir un número, no letras o símbolos!");
            sc.nextLine(); // Limpiamos el buffer del escáner para evitar un bucle infinito
            return 0;
        }
    }
}