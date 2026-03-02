package com.dbd.core;

import com.dbd.entidades.*;
import com.dbd.habilidades.Perk;
import com.dbd.habilidades.survis.*;
import com.dbd.habilidades.killers.*;

import java.util.ArrayList;
import java.util.Collections;
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

    public void iniciar() {
        System.out.println(CYAN + "\n--- SELECCIÓN DE SUPERVIVIENTES (Elige 3) ---" + RESET);
        while (supervivientes.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - supervivientes.size()));
            System.out.println(CYAN + "[1] Leon Kennedy  [2] Steve Harrington  [3] Feng Min  [4] Sable Ward" + RESET);
            System.out.print(">>> Elige: ");
            int op = sc.nextInt();
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
            int op = sc.nextInt();
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
        System.out.println(VERDE + "¡EQUIPOS LISTOS PARA LA PRUEBA!" + RESET);
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
                    s.decidirAccionIA(supervivientes, killers);
                    pausaDramatica();
                }
            }

            for (Personaje k : killers) {
                if (k.getVidaActual() > 0 && equipoVivo(supervivientes)) {
                    k.decidirAccionIA(killers, supervivientes);
                    pausaDramatica();
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
            String status = p.getVidaActual() > 0 ? VERDE + p.getVidaActual() + " HP" + RESET
                    : ROJO + " MUERTO" + RESET;
            System.out.print(CYAN + "[" + RESET + p.getNombrePersonaje() + ": " + status + CYAN + "] " + RESET);
        }
        System.out.print(ROJO + "\nKILLERS: " + RESET);
        for (Personaje p : killers) {
            String status = p.getVidaActual() > 0 ? VERDE + p.getVidaActual() + " HP" + RESET
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
            Thread.sleep(800);
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
}