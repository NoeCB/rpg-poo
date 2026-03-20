package com.dbd.core;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Util  {
    
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String MORADO = "\u001B[35m";
     public static int errorNumero(Scanner sc) {
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(
                    ROJO + "Error: Letras no permitidas. Inténtalo de nuevo:" + RESET);
            sc.nextLine();
            return 0;
        }
    }

public static void pausaDramatica() {
    try {
        int tiempoAleatorio = new Random().nextInt(501) + 500;
        System.out.println(AMARILLO + "..." + RESET);
        Thread.sleep(tiempoAleatorio);
    } catch (Exception e) {
    }
}
    
}
