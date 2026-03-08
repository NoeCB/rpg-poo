package com.dbd.core;

import com.dbd.arma.*;
import com.dbd.entidades.*;
import com.dbd.habilidades.Perk;
import com.dbd.habilidades.killers.*;
import com.dbd.habilidades.survis.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class MotorTrial {
    // Códigos de color
    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";
    public static final String MORADO = "\u001B[35m";

    private ArrayList<Personaje> supervivientes = new ArrayList<>();
    private ArrayList<Personaje> killers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    private int modoJuego;

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
        }
        Personaje supervivienteElegido = elegirPersonaje(survisDisp);
        supervivientes.add(supervivienteElegido);
        System.out.println("Has elegido a: " + supervivienteElegido.getNombrePersonaje());

        System.out.println("\nElige tu asesino:");
        ArrayList<Personaje> killersDisp = new ArrayList<>();
        killersDisp.add(new GhostFace());
        killersDisp.add(new Legion());
        killersDisp.add(new Onryo());
        killersDisp.add(new Animatronico());

        for (int i = 0; i < killersDisp.size(); i++) {
            Personaje p = killersDisp.get(i);
            System.out.println("- [" + (i + 1) + "] " + p.getNombrePersonaje() + " [Vida: " + p.getVidaMax() + "]");
        }
        Personaje asesinoElegido = elegirPersonaje(killersDisp);
        killers.add(asesinoElegido);
        System.out.println("Has elegido a: " + asesinoElegido.getNombrePersonaje());

        System.out.println(MORADO + "\n LA ENTIDAD ESTÁ REPARTIENDO LAS HABILIDADES AL AZAR..." + RESET);
        repartirPerks();
        System.out.println(AMARILLO + "\n LA ENTIDAD ESTÁ OTORGANDO ARMAS A LOS COMBATIENTES..." + RESET);
        repartirArmas();
        System.out.println(VERDE + "¡EQUIPOS LISTOS PARA LA PRUEBA!" + RESET);

        juegoManual(supervivienteElegido, asesinoElegido);
    }
private void juegoManual(Personaje survi, Personaje killer) {
    int ronda = 1;
    while (survi.getVidaActual() > 0 && killer.getVidaActual() > 0) {
        System.out.println(AMARILLO + "\n=================== RONDA " + ronda + " ===================" + RESET);
 
        survi.procesarEstados();
        killer.procesarEstados();

        if (survi.getVidaActual() > 0) {
            System.out.println(CYAN + "\n---> TURNO DE JUGADOR 1: " + survi.getNombrePersonaje() + RESET);
            ejecutarTurnoJugador(survi, killer);
        }


        if (killer.getVidaActual() <= 0) break;

        if (killer.getVidaActual() > 0) {
            System.out.println(ROJO + "\n---> TURNO DE JUGADOR 2: " + killer.getNombrePersonaje() + RESET);
            ejecutarTurnoJugador(killer, survi);
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
        
        int opcion = 0;
        try {
            opcion = sc.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println(ROJO + "Error: Letras no permitidas." + RESET);
        }
        sc.nextLine(); 

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
        System.out.print("Selecciona un personaje: ");
        int opcion = 0;
        try {
            opcion = sc.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out
                    .println("\u001B[31m" + " Error: ¡Debes introducir un número, no letras o símbolos!" + "\u001B[0m");
        }
        sc.nextLine();

        if (opcion >= 1 && opcion <= personaje.size()) {
            return personaje.get(opcion - 1);
        } else {
            System.out.println("Opción no válida, seleccionando el primer personaje por defecto.");
            return personaje.get(0);
        }
    }

    public void iniciar() {
        System.out.println(CYAN + "\n--- SELECCIÓN DE SUPERVIVIENTES (Elige 3) ---" + RESET);
        while (supervivientes.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - supervivientes.size()));
            System.out.println(CYAN + "[1] Leon Kennedy  [2] Steve Harrington  [3] Feng Min  [4] Sable Ward" + RESET);
            System.out.print(">>> Elige: ");
            int op = 0;
            try {
                op = sc.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println(ROJO + "Error: Letras no permitidas." + RESET);
            }
            sc.nextLine();

            switch (op) {
                case 1 -> supervivientes.add(new LeonKennedy());
                case 2 -> supervivientes.add(new SteveHarrington());
                case 3 -> supervivientes.add(new FengMin());
                case 4 -> supervivientes.add(new SableWard());
                default -> System.out.println(ROJO + "Opción no válida." + RESET);
            }
        }

        System.out.println(ROJO + "\n--- SELECCIÓN DE KILLERS (Elige 3) ---" + RESET);
        while (killers.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - killers.size()));
            System.out.println(ROJO + "[1] GhostFace  [2] Legion  [3] Onryo  [4] Animatrónico" + RESET);
            System.out.print(">>> Elige: ");
            int op = 0;
            try {
                op = sc.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println(ROJO + "Error: Letras no permitidas." + RESET);
            }
            sc.nextLine();

            switch (op) {
                case 1 -> killers.add(new GhostFace());
                case 2 -> killers.add(new Legion());
                case 3 -> killers.add(new Onryo());
                case 4 -> killers.add(new Animatronico());
                default -> System.out.println(ROJO + "Opción no válida." + RESET);
            }
        }

        System.out.println(MORADO + "\n LA ENTIDAD ESTÁ REPARTIENDO LAS HABILIDADES AL AZAR..." + RESET);
        repartirPerks();
        System.out.println(AMARILLO + "\n LA ENTIDAD ESTÁ OTORGANDO ARMAS A LOS COMBATIENTES..." + RESET);
        repartirArmas();
        System.out.println(VERDE + "¡EQUIPOS LISTOS PARA LA PRUEBA!" + RESET);
    }

    private void repartirArmas() {
        ArrayList<Arma> armasSolamenteSurvis = new ArrayList<>();
        armasSolamenteSurvis.add(new Pistola());
        armasSolamenteSurvis.add(new Conjuro());

        ArrayList<Arma> armasSolamenteKillers = new ArrayList<>();
        armasSolamenteKillers.add(new Hacha());
        armasSolamenteKillers.add(new Cuchillo());
        armasSolamenteKillers.add(new VinculoDeCondena());

        for (Personaje s : supervivientes) {
            Collections.shuffle(armasSolamenteSurvis);
            String nom = armasSolamenteSurvis.get(0).getNombreArma();
            if (nom.equals("Pistola"))
                s.setArma(new Pistola());
            else
                s.setArma(new Conjuro());
        }

        for (Personaje k : killers) {
            Collections.shuffle(armasSolamenteKillers);
            String nom = armasSolamenteKillers.get(0).getNombreArma();
            if (nom.equals("Hacha"))
                k.setArma(new Hacha());
            else if (nom.equals("Cuchillo"))
                k.setArma(new Cuchillo());
            else
                k.setArma(new VinculoDeCondena());
        }
    }

    private void repartirPerks() {
        for (Personaje s : supervivientes) {
            ArrayList<Perk> poolSurvis = new ArrayList<>();
            poolSurvis.add(new Adrenalina());
            poolSurvis.add(new Agilidad());
            poolSurvis.add(new DejaVu());
            poolSurvis.add(new DemuestraLoQueVales());
            poolSurvis.add(new ExtraOficial());
            poolSurvis.add(new Fajador());
            poolSurvis.add(new GolpeDecisivo());
            poolSurvis.add(new Inquebrantable());
            poolSurvis.add(new Liberacion());
            poolSurvis.add(new LoLograremos());
            poolSurvis.add(new Oportunidades());
            poolSurvis.add(new Parentesco());
            poolSurvis.add(new PersonajeSecundario());
            poolSurvis.add(new Resiliencia());
            poolSurvis.add(new Sprint());

            Collections.shuffle(poolSurvis);
            for (int i = 0; i < 4; i++)
                s.addPerk(poolSurvis.get(i));
        }

        for (Personaje k : killers) {
            ArrayList<Perk> poolKillers = new ArrayList<>();
            poolKillers.add(new AbrazoLugubre());
            poolKillers.add(new AmigosHastaElFin());
            poolKillers.add(new BarbacoaYChile());
            poolKillers.add(new Desconcierto());
            poolKillers.add(new EnNingunLugarParaEsconderse());
            poolKillers.add(new FuriaDelEspiritu());
            poolKillers.add(new IntervencionCorrupta());
            poolKillers.add(new MaleficioJuguete());
            poolKillers.add(new NadieEscapaDeLaMuerte());
            poolKillers.add(new PerseguidorLetal());
            poolKillers.add(new PimPamPum());
            poolKillers.add(new PuntoMuerto());
            poolKillers.add(new Resistencia());
            poolKillers.add(new ResonanciaDelDolor());
            poolKillers.add(new Sacudida());

            Collections.shuffle(poolKillers);
            for (int i = 0; i < 4; i++)
                k.addPerk(poolKillers.get(i));
        }
    }

    public void iniciarJuego() {
        int ronda = 1;
        while (equipoVivo(supervivientes) && equipoVivo(killers)) {
            System.out.println(AMARILLO + "\n=================== RONDA " + ronda + " ===================" + RESET);

            for (Personaje s : supervivientes) {
                if (s.getVidaActual() > 0 && equipoVivo(killers)) {
                    s.procesarEstados();
                    if (s.getVidaActual() > 0) {
                        s.decidirAccionIA(supervivientes, killers);
                        pausaDramatica();
                    }
                }
            }

            for (Personaje k : killers) {
                if (k.getVidaActual() > 0 && equipoVivo(supervivientes)) {
                    k.procesarEstados();
                    if (k.getVidaActual() > 0) {
                        k.decidirAccionIA(killers, supervivientes);
                        pausaDramatica();
                    }
                }
            }

            mostrarEstadoEquipos();
            ronda++;

            if (ronda > 50) {
                System.out.println(AMARILLO + "La batalla dura demasiado... ¡Empate técnico!" + RESET);
                break;
            }
        }
        anunciarGanador();
    }

    private boolean equipoVivo(ArrayList<Personaje> equipo) {
        return equipo.stream().anyMatch(p -> p.getVidaActual() > 0);
    }

    private void mostrarEstadoEquipos() {
        System.out.println(AMARILLO + "\n--- ESTADO DE LA PARTIDA ---" + RESET);
        System.out.print(CYAN + "SURVIS:  " + RESET);
        for (Personaje p : supervivientes) {
            String armaTxt = (p.getArma() != null) ? " [" + p.getArma().getNombreArma() + "]" : "";
            String status = p.getVidaActual() > 0 ? VERDE + p.getVidaActual() + " HP" + armaTxt + RESET
                    : ROJO + " MUERTO" + RESET;
            System.out.print(CYAN + "[" + RESET + p.getNombrePersonaje() + ": " + status + CYAN + "] " + RESET);
        }
        System.out.print(ROJO + "\nKILLERS: " + RESET);
        for (Personaje p : killers) {
            String armaTxt = (p.getArma() != null) ? " [" + p.getArma().getNombreArma() + "]" : "";
            String status = p.getVidaActual() > 0 ? VERDE + p.getVidaActual() + " HP" + armaTxt + RESET
                    : ROJO + " MUERTO" + RESET;
            System.out.print(ROJO + "[" + RESET + p.getNombrePersonaje() + ": " + status + ROJO + "] " + RESET);
        }
        System.out.println(AMARILLO + "\n----------------------------" + RESET);
    }

    private void anunciarGanador() {
        System.out.println(AMARILLO + "\n=========================================" + RESET);
        if (equipoVivo(supervivientes)) {
            System.out.println(CYAN + "  ¡LOS SUPERVIVIENTES HAN ESCAPADO!" + RESET);
        } else {
            System.out.println(ROJO + "  SACRIFICIO COMPLETADO. LA ENTIDAD GANA." + RESET);
        }
        System.out.println(AMARILLO + "=========================================" + RESET);
    }

    private void pausaDramatica() {
        try {
            int tiempoAleatorio = new Random().nextInt(501) + 500;
            System.out.println(AMARILLO + "..." + RESET);
            Thread.sleep(tiempoAleatorio);
        } catch (Exception e) {
        }
    }

    public void mostrarSupervivientes() {
        System.out.println(CYAN + "\n SUPERVIVIENTES DISPONIBLES:" + RESET);
        Personaje[] listaSurvis = { new LeonKennedy(), new SteveHarrington(), new FengMin(), new SableWard() };
        for (int i = 0; i < listaSurvis.length; i++) {
            System.out.println(" " + (i + 1) + ". " + listaSurvis[i].getNombrePersonaje() + " | Vida Base: " + VERDE
                    + listaSurvis[i].getVidaActual() + " HP" + RESET);
        }
        System.out.println(CYAN + "-------------------------------------------------" + RESET);
    }

    public void mostrarAsesinos() {
        System.out.println(ROJO + "\n KILLERS DISPONIBLES (LA ENTIDAD):" + RESET);
        Personaje[] listaKillers = { new GhostFace(), new Legion(), new Onryo(), new Animatronico() };
        for (int i = 0; i < listaKillers.length; i++) {
            System.out.println(" " + (i + 1) + ". " + listaKillers[i].getNombrePersonaje() + " | Vida Base: " + VERDE
                    + listaKillers[i].getVidaActual() + " HP" + RESET);
        }
        System.out.println(ROJO + "-------------------------------------------------" + RESET);
    }

    public void configurarPartida() {
        supervivientes.clear();
        killers.clear();
    }
}
