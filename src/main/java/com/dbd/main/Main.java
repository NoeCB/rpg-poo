package com.dbd.main;

import com.dbd.core.MotorTrial;
import com.dbd.core.Util;
import static com.dbd.core.Util.*;
import com.dbd.entidades.Personaje;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * Clase principal del juego Dead by Daylight RPG (Versión Consola).
 * Ahora se ejecuta como un hilo dentro de Spring Boot si le pasamos el flag --console.
 */
@Component
public class Main implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // Solo arrancar consola si se lo indicamos
        if (!Arrays.asList(args).contains("--console")) {
            return;
        }

        Util.iniciarMusica("resources/musica_dbd.wav");
        Scanner sc = new Scanner(System.in);
        MotorTrial prueba = new MotorTrial();
        com.dbd.dao.GestorPersistencia gestor = com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class);
        int opcion = 0;

        do {
            System.out.println(ROJO + "\n=============================================" + RESET);
            System.out.println(ROJO + "   --- BIENVENIDO A DEAD BY DAYLIGHT ---" + RESET);
            System.out.println(ROJO + "=============================================" + RESET);
            System.out.println(VERDE + "[1] Nueva Partida" + RESET);
            System.out.println(AMARILLO + "[2] Cargar Partida" + RESET);
            System.out.println(MORADO + "[3] Estadísticas Globales (Historial de Facción)" + RESET);
            System.out.println(CYAN + "[4] Extras (Pabellón de Personajes)" + RESET);
            System.out.println("[5] Salir del juego");
            System.out.print(AMARILLO + ">>> Selecciona una opción: " + RESET);

            opcion = errorNumero(sc);

            switch (opcion) {
                case 1:
                    System.out.println(VERDE + "\n--- NUEVA PARTIDA ---" + RESET);
                    prueba.configurarPartida(); // Preparar motor
                    
                    int modoNueva = 0;
                    do {
                        System.out.println("[1] Modo Manual");
                        System.out.println("[2] Modo Automático (Simulación 3vs3)");
                        System.out.println("[0] Cancelar");
                        System.out.print(">>> Opción: ");
                        modoNueva = errorNumero(sc);
                        
                        if (modoNueva == 1) {
                            prueba.setIdRanuraActual(-1, "manual"); // Empieza sin ranura asignada hasta que guarde
                            prueba.iniciarManual();
                            prueba.iniciarJuegoManual(1); // Ronda 1
                            break;
                        } else if (modoNueva == 2) {
                            prueba.setIdRanuraActual(-1, "automatico");
                            prueba.iniciarAleatorio();
                            prueba.iniciarJuegoAutomatizado(1);
                            break;
                        }
                    } while (modoNueva != 0);
                    break;
                    
                case 2:
                    System.out.println(AMARILLO + "\n--- CARGAR PARTIDA ---" + RESET);
                    gestor.listarPartidas();
                    System.out.print(AMARILLO + "Introduce el ID de la ranura a cargar (1-3) o 0 para cancelar: " + RESET);
                    int slotCarga = errorNumero(sc);
                    
                    if (slotCarga >= 1 && slotCarga <= 3) {
                        ArrayList<Personaje> survisCargados = new ArrayList<>();
                        ArrayList<Personaje> killersCargados = new ArrayList<>();
                        
                        Object[] datos = gestor.cargarPartidaCompleta(slotCarga, survisCargados, killersCargados);
                        if (datos != null) {
                            int rondaGuardada = (int) datos[0];
                            String modoGuardado = (String) datos[1];
                            
                            prueba.configurarPartida();
                            prueba.setIdRanuraActual(slotCarga, modoGuardado);
                            prueba.setSupervivientes(survisCargados);
                            prueba.setKillers(killersCargados);
                            
                            prueba.mostrarResumenTactico(rondaGuardada); // Esperar a que el jugador entienda
                            
                            if (modoGuardado.equals("manual")) {
                                prueba.iniciarJuegoManual(rondaGuardada);
                            } else {
                                prueba.iniciarJuegoAutomatizado(rondaGuardada);
                            }
                        }
                    }
                    break;
                    
                case 3:
                    gestor.mostrarEstadisticasGlobales();
                    break;
                    
                case 4:
                    int opExtra = 0;
                    do {
                        System.out.println(CYAN + "\n--- EXTRAS ---" + RESET);
                        System.out.println("[1] Mirar Personajes (Supervivientes)");
                        System.out.println("[2] Mirar Asesinos (Killers)");
                        System.out.println("[3] Ver Logros Desbloqueados");
                        System.out.println("[0] Volver al menú principal");
                        System.out.print(">>> Opción: ");
                        opExtra = errorNumero(sc);
                        
                        if (opExtra == 1) prueba.mostrarSupervivientes();
                        else if (opExtra == 2) prueba.mostrarAsesinos();
                        else if (opExtra == 3) {
                            System.out.println(AMARILLO + "\n=== SALÓN DE LOS LOGROS ===" + RESET);
                            ArrayList<com.dbd.core.Logro> logros = gestor.cargarLogros();
                            for (com.dbd.core.Logro l : logros) {
                                String estado = l.isConseguido() ? VERDE + "[DESBLOQUEADO]" + RESET : ROJO + "[BLOQUEADO]" + RESET;
                                System.out.println(estado + " " + CYAN + l.getNombre() + RESET + " - " + l.getDescripcion());
                            }
                            System.out.println(AMARILLO + "===========================\n" + RESET);
                            Util.pedirEnter(sc);
                        }
                        
                    } while (opExtra != 0);
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