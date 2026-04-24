package com.dbd.core;
import java.io.File;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Clase de utilidades generales para el proyecto RPG basado en el uni.
 * <p>
 * Proporciona constantes de color para la consola y métodos estáticos
 * para la gestión de errores de entrada y efectos visuales.
 * </p>
 * * @author Noelia Cantador y Luis Lázaro.
 * @version 1.0
 */
public class Util  {
    /** Código ANSI para restablecer el color de la consola. */
    public static final String RESET = "\u001B[0m";
    /** Código ANSI para color blanco. */
    public static final String BLANCO = "\u001B[37m";
    /** Código ANSI para color rojo (usado en errores). */
    public static final String ROJO = "\u001B[31m";
    /** Código ANSI para color verde. */
    public static final String VERDE = "\u001B[32m";
    /** Código ANSI para color amarillo. */
    public static final String AMARILLO = "\u001B[33m";
    /** Código ANSI para color cyan. */
    public static final String CYAN = "\u001B[36m";
    /** Código ANSI para color morado. */
    public static final String MORADO = "\u001B[35m";


    /**
     * Inicia la reproducción de música de fondo en un hilo independiente.
     * <p>
     * Utiliza la API {@link javax.sound.sampled} para cargar archivos WAV.
     * La reproducción se configura en bucle continuo.
     * </p>
     * @param rutaArchivo Dirección relativa del archivo de sonido (ej: "resources/audio.wav").
     */
public static void iniciarMusica(String rutaArchivo) {
        new Thread(() -> {
            try {
                File archivo = new File(rutaArchivo);
                AudioInputStream stream = AudioSystem.getAudioInputStream(archivo);
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                
                // Reproducir en bucle infinito
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(ROJO + "Error de audio: " + e.getMessage() + RESET);
            }
        }).start();
    }

        /**
     * Valida que la entrada del usuario sea un número entero.
     * <p>
     * Si el usuario introduce texto en lugar de un número, captura la excepción
     * {@link InputMismatchException}, muestra un mensaje de error en rojo y
     * limpia el búfer del escáner.
     * </p>
     * * @param sc El objeto {@link Scanner} activo para leer la entrada.
     * @return El número entero introducido o 0 si ocurrió un error.
     */
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
/**
     * Genera una interrupción en la ejecución para simular suspense.
     * <p>
     * El tiempo de espera es aleatorio, variando entre 500 y 1000 milisegundos.
     * Durante la espera, se imprimen puntos suspensivos en color amarillo.
     * </p>
     */
public static void pausaDramatica() {
    try {
        int tiempoAleatorio = new Random().nextInt(501) + 500;
        System.out.println(AMARILLO + "..." + RESET);
        Thread.sleep(tiempoAleatorio);
    } catch (Exception e) {
    }
}

    /**
     * Espera a que el usuario pulse Enter para continuar.
     * @param sc El escáner para leer.
     */
    public static void pedirEnter(Scanner sc) {
        System.out.println(CYAN + "\nPulsa ENTER para continuar..." + RESET);
        sc.nextLine(); // Para consumir salto de línea previo si lo hay
        sc.nextLine(); // Para esperar el enter
    }

    /**
     * Limpia la consola imprimiendo múltiples saltos de línea (simulación simple).
     */
    public static void limpiarPantalla() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

    /**
     * Genera un número aleatorio entre min y max (ambos incluidos).
     * @param min Valor mínimo
     * @param max Valor máximo
     * @return El número generado
     */
    public static int numAleatorio(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
    
}
