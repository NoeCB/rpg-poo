package com.dbd.core;

import com.dbd.arma.*;
import static com.dbd.core.Util.*;
import com.dbd.entidades.*;
import com.dbd.habilidades.Perk;
import com.dbd.habilidades.killers.*;
import com.dbd.habilidades.survis.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MotorTrial {
    private ArrayList<Personaje> supervivientes = new ArrayList<>();
    private ArrayList<Personaje> killers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public void iniciarJuegoManual() {
        int ronda = 1;
        while (equipoVivo(supervivientes) && equipoVivo(killers)) {
            System.out.println(AMARILLO + "\n=================== RONDA " + ronda + " ===================" + RESET);

            for (int i = 0; i < supervivientes.size(); i++) {
                Personaje s = supervivientes.get(i);
                if (s.getVidaActual() > 0 && equipoVivo(killers)) {
                    s.procesarEstados();
                    if (s.getVidaActual() > 0) {
                        System.out.println(CYAN + "\n---> TURNO DE: " + s.getNombrePersonaje() + RESET);
                        ejecutarTurnoManual(s, killers);
                    }
                }
            }

            for (int i = 0; i < killers.size(); i++) {
                Personaje k = killers.get(i);
                if (k.getVidaActual() > 0 && equipoVivo(supervivientes)) {
                    k.procesarEstados();
                    if (k.getVidaActual() > 0) {
                        System.out.println(ROJO + "\n---> TURNO DE: " + k.getNombrePersonaje() + RESET);
                        ejecutarTurnoManual(k, supervivientes);
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

    private void ejecutarTurnoManual(Personaje atacante, ArrayList<Personaje> equipoRival) {
        boolean turnoCompletado = false;
        while (!turnoCompletado) {
            System.out.println("¿Qué deseas hacer?");
            System.out.println("1. Atacar");
            System.out.println("2. Usar Perk (Habilidad)");
            System.out.println("3. Postura Defensiva");
            System.out.print("Elige una opción: ");
            int opcion = errorNumero(sc);

            switch (opcion) {
                case 1:
                    Personaje objetivoAtk = elegirObjetivo(equipoRival);
                    String armaTxt = (atacante.getArma() != null) ? atacante.getArma().getNombreArma() : "puños desnudos";
                    System.out.println(atacante.getNombrePersonaje() + " ataca con " + armaTxt + " a " + objetivoAtk.getNombrePersonaje() + "!");
                    
                    int danio = (atacante.getArma() != null) ? atacante.getArma().getDanioBase() : 15;
                    objetivoAtk.recibirDanio(danio); // Nota: el daño base se pasa, y Personaje.recibirDanio comprueba escudos / evasion.
                    
                    turnoCompletado = true;
                    break;
                case 2:
                    if (atacante.getPerks().isEmpty()) {
                        System.out.println(ROJO + "Este personaje no tiene Perks asignadas." + RESET);
                    } else {
                        System.out.println("Elige qué Perk usar:");
                        for (int i = 0; i < atacante.getPerks().size(); i++) {
                            Perk p = atacante.getPerks().get(i);
                            System.out.println("[" + (i + 1) + "] " + p.getNombre() + " (Usos: " + p.getUsos() + ")");
                        }
                        System.out.println("[" + (atacante.getPerks().size() + 1) + "] Cancelar");
                        int opPerk = errorNumero(sc);
                        
                        if (opPerk >= 1 && opPerk <= atacante.getPerks().size()) {
                            Perk perkElegida = atacante.getPerks().get(opPerk - 1);
                            if (perkElegida.getUsos() > 0) {
                                Personaje objetivoPerk = elegirObjetivo(equipoRival); 
                                perkElegida.lanzar(atacante, objetivoPerk);
                                perkElegida.consumirUso();
                                turnoCompletado = true;
                            } else {
                                System.out.println(ROJO + "No quedan usos de esta Perk." + RESET);
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println(atacante.getNombrePersonaje() + " adopta postura defensiva...");
                    atacante.setDefendiendo(true);
                    turnoCompletado = true;
                    break;
                default:
                    System.out.println(ROJO + "Opción inválida." + RESET);
            }
        }
    }

    private Personaje elegirObjetivo(ArrayList<Personaje> equipoRival) {
        ArrayList<Personaje> vivos = new ArrayList<>();
        for (int i = 0; i < equipoRival.size(); i++) {
            if (equipoRival.get(i).getVidaActual() > 0) {
                vivos.add(equipoRival.get(i));
            }
        }
        
        while (true) {
            System.out.println("Selecciona un objetivo:");
            for (int i = 0; i < vivos.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + vivos.get(i).getNombrePersonaje() + " (" + vivos.get(i).getVidaActual() + " HP)");
            }
            int op = errorNumero(sc);
            if (op >= 1 && op <= vivos.size()) {
                return vivos.get(op - 1);
            } else {
                System.out.println(ROJO + "Opción no válida. Por favor, elige un número de la lista." + RESET);
            }
        }
    }
    public void iniciarManual() {
        System.out.println(CYAN + "\n--- SELECCIÓN DE SUPERVIVIENTES (Elige 3) ---" + RESET);
        while (supervivientes.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - supervivientes.size()));
            System.out.println(CYAN + "[1] Leon Kennedy  [2] Steve Harrington  [3] Feng Min  [4] Sable Ward" + RESET);
            System.out.print(">>> Elige: ");
            int op = errorNumero(sc);

            switch (op) {
                case 1:
                    supervivientes.add(new LeonKennedy());
                    break;
                case 2:
                    supervivientes.add(new SteveHarrington());
                    break;
                case 3:
                    supervivientes.add(new FengMin());
                    break;
                case 4:
                    supervivientes.add(new SableWard());
                    break;
                default:
                    System.out.println(ROJO + "Opción no válida." + RESET);
                    break;
            }
        }

        System.out.println(ROJO + "\n--- SELECCIÓN DE KILLERS (Elige 3) ---" + RESET);
        while (killers.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - killers.size()));
            System.out.println(ROJO + "[1] GhostFace  [2] Legion  [3] Onryo  [4] Animatrónico" + RESET);
            System.out.print(">>> Elige: ");

            int op = errorNumero(sc);

            switch (op) {
                case 1:
                    killers.add(new GhostFace());
                    break;
                case 2:
                    killers.add(new Legion());
                    break;
                case 3:
                    killers.add(new Onryo());
                    break;
                case 4:
                    killers.add(new Animatronico());
                    break;
                default:
                    System.out.println(ROJO + "Opción no válida." + RESET);
                    break;
            }
        }

        System.out.println(MORADO + "\n LA ENTIDAD ESTÁ REPARTIENDO LAS HABILIDADES AL AZAR..." + RESET);
        repartirPerks();
        System.out.println(AMARILLO + "\n LA ENTIDAD ESTÁ OTORGANDO ARMAS A LOS COMBATIENTES..." + RESET);
        repartirArmas();
        System.out.println(VERDE + "¡EQUIPOS LISTOS PARA LA PRUEBA!" + RESET);
    }

    public void iniciarAleatorio() {
        ArrayList<Personaje> survisDisp = new ArrayList<>();
        survisDisp.add(new LeonKennedy());
        survisDisp.add(new SteveHarrington());
        survisDisp.add(new FengMin());
        survisDisp.add(new SableWard());

        ArrayList<Personaje> killersDisp = new ArrayList<>();
        killersDisp.add(new GhostFace());
        killersDisp.add(new Legion());
        killersDisp.add(new Onryo());
        killersDisp.add(new Animatronico());

        Collections.shuffle(survisDisp);
        Collections.shuffle(killersDisp);

        for (int i = 0; i < 3; i++) {
            supervivientes.add(survisDisp.get(i));
            killers.add(killersDisp.get(i));
        }

        System.out.println(MORADO + "\n LA ENTIDAD ESTÁ CREANDO LOS EQUIPOS AL AZAR..." + RESET);
        repartirPerks();
        repartirArmas();
        System.out.println(VERDE + "¡SIMULACIÓN LISTA!" + RESET);
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
        for (Personaje p : equipo) {
            if (p.getVidaActual() > 0) {
                return true;
            }
        }
        return false;
    }

    private String dibujarBarraVida(int actual, int max) {
        if (actual < 0) actual = 0;
        int maxBarras = 10;
        int barrasLlenas = (int) Math.round((double) actual / max * maxBarras);
        
        String barra = "[";
        for (int i = 0; i < maxBarras; i++) {
            if (i < barrasLlenas) {
                barra += "█";
            } else {
                barra += "░";
            }
        }
        barra += "]";
        return barra;
    }

    private void mostrarEstadoEquipos() {
        System.out.println(AMARILLO + "\n--- ESTADO DE LA PARTIDA ---" + RESET);
        System.out.print(CYAN + "SUPERVIVIENTES:\n" + RESET);
        for (int i = 0; i < supervivientes.size(); i++) {
            Personaje p = supervivientes.get(i);
            String armaTxt = (p.getArma() != null) ? " [" + p.getArma().getNombreArma() + "]" : "";
            
            String status = "";
            if (p.getVidaActual() > 0) {
                status = VERDE + p.getVidaActual() + "/" + p.getVidaMax() + " HP " + dibujarBarraVida(p.getVidaActual(), p.getVidaMax()) + armaTxt + RESET;
            } else {
                status = ROJO + " MUERTO " + dibujarBarraVida(0, p.getVidaMax()) + RESET;
            }
            System.out.println(CYAN + "> " + p.getNombrePersonaje() + ": " + status);
        }
        System.out.print(ROJO + "\nKILLERS (LA ENTIDAD):\n" + RESET);
        for (int i = 0; i < killers.size(); i++) {
            Personaje p = killers.get(i);
            String armaTxt = (p.getArma() != null) ? " [" + p.getArma().getNombreArma() + "]" : "";
            
            String status = "";
            if (p.getVidaActual() > 0) {
                status = VERDE + p.getVidaActual() + "/" + p.getVidaMax() + " HP " + dibujarBarraVida(p.getVidaActual(), p.getVidaMax()) + armaTxt + RESET;
            } else {
                status = ROJO + " MUERTO " + dibujarBarraVida(0, p.getVidaMax()) + RESET;
            }
            System.out.println(ROJO + "> " + p.getNombrePersonaje() + ": " + status);
        }
        System.out.println(AMARILLO + "----------------------------" + RESET);
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
