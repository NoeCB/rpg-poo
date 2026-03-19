package com.dbd.core;

import com.dbd.entidades.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MotorTrial {
    private ArrayList<Personaje> supervivientes = new ArrayList<>();
    private ArrayList<Personaje> killers = new ArrayList<>();
<<<<<<< Updated upstream
    private Scanner sc = new Scanner(System.in);
    private int modoJuego;

<<<<<<< HEAD



    // public void iniciarJuego() {
    //     System.out.println("=== INICIO DE LA PRUEBA ===");
    //     Personaje leon = supervivientes.get(0);
    //     Personaje sable = enemigos.get(0);

    //     System.out.println("\n>>> FASE DE ACCIONES <<<");
    //     // Turno de Sable
    //     sable.accion();
    //     sable.getPerks().get(0).lanzar(sable, leon); // Sable lanza Maldición a Leon

    //     // Turno de Leon
    //     System.out.println();
    //     leon.accion();
    //     leon.getPerks().get(0).lanzar(leon, sable); // Leon lanza Granada a Sable

    //     System.out.println("\n>>> FASE DE ESTADOS (Pasa el tiempo) <<<");
    //     // Aquí es donde el veneno quita vida automáticamente
    //     leon.procesarEstados();
    //     sable.procesarEstados();

    //     System.out.println("\n>>> RESUMEN FIN DE RONDA <<<");
    //     System.out.println(leon.getNombrePersonaje() + ": " + leon.getVidaActual() + " HP");
    //     System.out.println(sable.getNombrePersonaje() + ": " + sable.getVidaActual() + " HP");
    // }

    public void iniciarJuegoManual() {
        System.out.println("Elige tu superviviente:");
        for (Personaje p : supervivientes) {
            System.out.println("- " + p.getNombrePersonaje() + " [Vida: " + p.getVidaBase() + "]");
=======
        private Scanner sc = new Scanner(System.in);
    

    public void iniciarJuegoManual() {
        System.out.println("Elige tu superviviente:");
        ArrayList<Personaje> survisDisp = new ArrayList<>();
        survisDisp.add(new LeonKennedy());
        survisDisp.add(new SteveHarrington());
        survisDisp.add(new FengMin());
        survisDisp.add(new SableWard());
        
        for (int i = 0; i < survisDisp.size(); i++) {
            System.out.println("- [" + (i + 1) + "] " + survisDisp.get(i).getNombrePersonaje() + " [Vida: "
                    + survisDisp.get(i).getVidaMax() + "]");
>>>>>>> Stashed changes
        }
       Personaje supervivienteElegido = elegirPersonaje(supervivientes);
    System.out.println("Has elegido a: " + supervivienteElegido.getNombrePersonaje());
        System.out.println("Elige tu asesino:");

       for(Personaje p : enemigos) {
        System.out.println(p.getNombrePersonaje() + " [Daño: " + p.getDanioBase() + "]");
       }
       Personaje asesinoElegido = elegirPersonaje(enemigos);
    System.out.println("Has elegido a: " + asesinoElegido.getNombrePersonaje());
        
        
    }
<<<<<<< Updated upstream
    private Personaje elegirPersonaje(ArrayList<Personaje> personaje) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Selecciona un personaje: ");
        int opcion = sc.nextInt();
        sc.nextLine(); 
=======
private void juegoManual(Personaje survi, Personaje killer) {
    int ronda = 1;
    boolean empiezaJugador = Math.random() < 0.5;
    Personaje primero = empiezaJugador ? survi : killer;
    Personaje segundo = empiezaJugador ? killer : survi;
    
    System.out.println(MORADO + "\n¡Empieza atacando: " + primero.getNombrePersonaje() + RESET);

    while (survi.getVidaActual() > 0 && killer.getVidaActual() > 0) {
        System.out.println(AMARILLO + "\n=================== RONDA " + ronda + " ===================" + RESET);
 
        primero.procesarEstados();
        segundo.procesarEstados();

        if (primero.getVidaActual() > 0) {
            System.out.println(CYAN + "\n---> TURNO DE: " + primero.getNombrePersonaje() + RESET);
            ejecutarTurnoJugador(primero, segundo);
        }

        if (segundo.getVidaActual() <= 0) 
            break;

        if (segundo.getVidaActual() > 0) {
            System.out.println(ROJO + "\n---> TURNO DE: " + segundo.getNombrePersonaje() + RESET);
            ejecutarTurnoJugador(segundo, primero);
        }

        mostrarEstadoEquipos();
        ronda++;
    }

    anunciarGanador();
}
private void ejecutarTurnoJugador(Personaje atacante, Personaje defensor) {
    boolean turnoCompletado = false;
    
    while (!turnoCompletado) {
        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. Atacar" );
        System.out.println("2. Usar Habilidad");
        System.out.println("3. Pasar turno");
        System.out.print("Elige una opción: ");

     int opcion = errorNumero(sc);

        switch (opcion) {
            case 1:

                System.out.println(atacante.getNombrePersonaje() + " ataca con " + atacante.getArma().getNombreArma() + " a " + defensor.getNombrePersonaje() + "!");
                
              
                int danio = atacante.getArma().getDanioBase(); 
              
                int nuevaVida = defensor.getVidaActual() - danio;
                defensor.setVidaActual(nuevaVida);
                
                System.out.println(ROJO + " Ha causado " + danio + " puntos de daño." + RESET);
                
                turnoCompletado = true;
                break;
            case 2:

                System.out.println(atacante.getNombrePersonaje() + " canaliza el poder de la Entidad...");
                turnoCompletado = true;
                break;
                
            case 3:
                System.out.println(atacante.getNombrePersonaje() + " decide adoptar una postura defensiva y pasa su turno.");

                turnoCompletado = true;
                break;
                
            default:
                System.out.println(ROJO + "Opción inválida. Inténtalo de nuevo." + RESET);
                break; 
        }
    }
}
   private Personaje elegirPersonaje(ArrayList<Personaje> personaje) {
    while (true) { 
        System.out.print("Selecciona un personaje: ");
        int opcion = errorNumero(sc);

>>>>>>> Stashed changes
        if (opcion >= 1 && opcion <= personaje.size()) {
            return personaje.get(opcion - 1); 
        } else {
            System.out.println(ROJO + "Opción no válida. Por favor, elige un número del 1 al " + personaje.size() + "." + RESET);
        }
    }
}
<<<<<<< Updated upstream
    
=======
=======

>>>>>>> Stashed changes
    public void iniciar() {
        System.out.println("=========================================");
        System.out.println("      DEAD BY DAYLIGHT: THE RPG (3vs3)");
        System.out.println("=========================================");
        
        System.out.println("Selecciona modo: [1] Automático | [2] Manual");
        modoJuego = sc.nextInt();

        // SELECCIÓN DE EQUIPO SUPERVIVIENTE
        System.out.println("\n--- SELECCIÓN DE SUPERVIVIENTES (Elige 3) ---");
        while(supervivientes.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - supervivientes.size()));
            System.out.println("[1] Leon Kennedy  [2] Steve Harrington  [3] Feng Min  [4] Sable Ward");
            System.out.print(">>> Elige: ");
            int op = sc.nextInt();
            switch(op) {
                case 1 -> supervivientes.add(new LeonKennedy());
                case 2 -> supervivientes.add(new SteveHarrington());
                case 3 -> supervivientes.add(new FengMin());
                case 4 -> supervivientes.add(new SableWard());
                default -> System.out.println("Opción no válida.");
            }
        }

        // SELECCIÓN DE EQUIPO KILLER
        System.out.println("\n--- SELECCIÓN DE KILLERS (Elige 3) ---");
        while(killers.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - killers.size()));
            System.out.println("[1] GhostFace  [2] Legion  [3] Onryo  [4] Animatrónico");
            System.out.print(">>> Elige: ");
            int op = sc.nextInt();
            switch(op) {
                case 1 -> killers.add(new GhostFace());
                case 2 -> killers.add(new Legion());
                case 3 -> killers.add(new Onryo());
                case 4 -> killers.add(new Animatronico());
                default -> System.out.println("Opción no válida.");
            }
        }
        System.out.println("\n¡EQUIPOS LISTOS PARA LA PRUEBA!");
    } 

    public void iniciarJuego() {
        int ronda = 1;
        while (equipoVivo(supervivientes) && equipoVivo(killers)) {
            System.out.println("\n===== RONDA " + ronda + " =====");

            // Turno Supervivientes
            for (Personaje s : supervivientes) {
                if (s.getVidaActual() > 0 && equipoVivo(killers)) {
                    s.decidirAccionIA(supervivientes, killers);
                    pausaDramatica();
                }
            }

            // Turno Killers
            for (Personaje k : killers) {
                if (k.getVidaActual() > 0 && equipoVivo(supervivientes)) {
                    k.decidirAccionIA(killers, supervivientes);
                    pausaDramatica();
                }
            }

            mostrarEstadoEquipos();
            ronda++;
            
            if(ronda > 50) {
                System.out.println("La batalla dura demasiado... ¡Empate técnico!");
                break;
            }
        }
        anunciarGanador();
    }

    private boolean equipoVivo(ArrayList<Personaje> equipo) {
        return equipo.stream().anyMatch(p -> p.getVidaActual() > 0);
    }

    private void mostrarEstadoEquipos() {
        System.out.println("\n--- ESTADO DE LA PARTIDA ---");
        System.out.print("SURVIS:  ");
        for(Personaje p : supervivientes) {
            String status = p.getVidaActual() > 0 ? p.getVidaActual() + " HP" : "MUERTO";
            System.out.print("[" + p.getNombrePersonaje() + ": " + status + "] ");
        }
        System.out.print("\nKILLERS: ");
        for(Personaje p : killers) {
            String status = p.getVidaActual() > 0 ? p.getVidaActual() + " HP" : "MUERTO";
            System.out.print("[" + p.getNombrePersonaje() + ": " + status + "] ");
        }
        System.out.println("\n----------------------------");
    }

    private void anunciarGanador() {
        System.out.println("\n=========================================");
        if (equipoVivo(supervivientes)) {
            System.out.println("¡LOS SUPERVIVIENTES HAN ESCAPADO!");
        } else {
            System.out.println("SACRIFICIO COMPLETADO. LA ENTIDAD GANA.");
        }
        System.out.println("=========================================");
    }

    private void pausaDramatica() {
        try { Thread.sleep(800); } catch (Exception e) {}
    }
    // --- MÉTODOS PARA EL MENÚ PRINCIPAL ---

    public void mostrarSupervivientes() {
        System.out.println("\n SUPERVIVIENTES DISPONIBLES:");
        
        // Creamos un array temporal con objetos reales para extraer sus datos exactos
        Personaje[] listaSurvis = {
            new LeonKennedy(),
            new SteveHarrington(),
            new FengMin(),
            new SableWard()
        };

        // Recorremos el array y usamos las funciones (getters) de la clase Personaje
        for (int i = 0; i < listaSurvis.length; i++) {
            System.out.println(" " + (i + 1) + ". " + listaSurvis[i].getNombrePersonaje() + 
                               " | Vida Base: " + listaSurvis[i].getVidaActual() + " HP");
        }
        System.out.println("-------------------------------------------------");
    }

    public void mostrarAsesinos() {
        System.out.println("\n KILLERS DISPONIBLES (LA ENTIDAD):");
        
        // Lo mismo para los asesinos
        Personaje[] listaKillers = {
            new GhostFace(),
            new Legion(),
            new Onryo(),
            new Animatronico()
        };

        for (int i = 0; i < listaKillers.length; i++) {
            System.out.println(" " + (i + 1) + ". " + listaKillers[i].getNombrePersonaje() + 
                               " | Vida Base: " + listaKillers[i].getVidaActual() + " HP");
        }
        System.out.println("-------------------------------------------------");
    }
} // Esta es la llave que cierra la clase al final
>>>>>>> 1384e92e82f1003fcd6966331097bb08e2b6eb7c
