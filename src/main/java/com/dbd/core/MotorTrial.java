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

/**
 * Clase que gestiona el motor de combate.
 * Actualizado para soportar persistencia avanzada, menú de pausa intermodular y resumen táctico.
 */
public class MotorTrial {
    private ArrayList<Personaje> supervivientes = new ArrayList<>();
    private ArrayList<Personaje> killers = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);
    
    private int idRanuraActual = -1;
    private String modoActual = "manual";

    public ArrayList<Personaje> getSupervivientes() {
        return supervivientes;
    }

    public ArrayList<Personaje> getKillers() {
        return killers;
    }

    public void setIdRanuraActual(int id, String modo) {
        this.idRanuraActual = id;
        this.modoActual = modo;
    }

    public void setSupervivientes(ArrayList<Personaje> s) {
        this.supervivientes = s;
    }

    public void setKillers(ArrayList<Personaje> k) {
        this.killers = k;
    }

    /**
     * Muestra un Resumen Táctico después de cargar exitosamente de la base de datos.
     */
    public void mostrarResumenTactico(int ronda) {
        System.out.println(VERDE + "\n=============================================" + RESET);
        System.out.println(VERDE + "   RESUMEN TÁCTICO - CARGA COMPLETADA" + RESET);
        System.out.println(VERDE + "=============================================" + RESET);
        System.out.println("-> Modo de juego: " + modoActual.toUpperCase());
        System.out.println("-> Ronda a reanudar: " + ronda);
        mostrarEstadoEquipos();
        System.out.println(AMARILLO + ">>> Partida lista. Presiona ENTER para continuar..." + RESET);
        try { System.in.read(); } catch (Exception e) {}
    }

    public void iniciarJuegoManual(int rondaInicio) {
        int ronda = rondaInicio;
        boolean abandonar = false;
        
        while (equipoVivo(supervivientes) && equipoVivo(killers) && !abandonar) {
            System.out.println(AMARILLO + "\n=================== RONDA " + ronda + " ===================" + RESET);

            for (int i = 0; i < supervivientes.size(); i++) {
                Personaje s = supervivientes.get(i);
                if (s.getVidaActual() > 0 && equipoVivo(killers)) {
                    s.procesarEstados();
                    if (s.getVidaActual() > 0) {
                        System.out.println(CYAN + "\n---> TURNO DE: " + s.getNombrePersonaje() + RESET);
                        abandonar = ejecutarTurnoManual(s, killers, ronda);
                        if (abandonar) break;
                    }
                }
            }

            if (!abandonar) {
                for (int i = 0; i < killers.size(); i++) {
                    Personaje k = killers.get(i);
                    if (k.getVidaActual() > 0 && equipoVivo(supervivientes)) {
                        k.procesarEstados();
                        if (k.getVidaActual() > 0) {
                            System.out.println(ROJO + "\n---> TURNO DE: " + k.getNombrePersonaje() + RESET);
                            abandonar = ejecutarTurnoManual(k, supervivientes, ronda);
                            if (abandonar) break;
                        }
                    }
                }
            }

            if (abandonar) break;

            mostrarEstadoEquipos();
            
            if (idRanuraActual != -1) {
                boolean guardado = com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).guardarPartida(idRanuraActual, ronda, modoActual, false, supervivientes, killers);
                if(guardado) {
                    System.out.println(MORADO + "--> [BD] Progreso guardado automáticamente (Ranura " + idRanuraActual + ")." + RESET);
                }
            }
            
            ronda++;
            if (ronda > 50) {
                System.out.println(AMARILLO + "La batalla dura demasiado... ¡Empate técnico!" + RESET);
                break;
            }
        }
        if (!abandonar) anunciarGanador(ronda);
    }

    /**
     * Devuelve TRUE si el jugador elige abandonar y volver al menú principal.
     */
    private boolean ejecutarTurnoManual(Personaje atacante, ArrayList<Personaje> equipoRival, int rondaActual) {
        boolean turnoCompletado = false;
        while (!turnoCompletado) {
            System.out.println(CYAN + "¿Qué deseas hacer?" + RESET);
            System.out.println(BLANCO + "0. Menú de Pausa (Guardar/Salir)" + RESET);
            System.out.println(AMARILLO + "1. Atacar" + RESET);
            System.out.println(MORADO + "2. Usar Perk (Habilidad)" + RESET);
            System.out.println(VERDE + "3. Postura Defensiva" + RESET);
            System.out.print(CYAN + "Elige una opción: " + RESET);
            int opcion = errorNumero(sc);

            switch (opcion) {
                case 0:
                    boolean salir = abrirMenuPausa(rondaActual);
                    if (salir) return true; 
                    break; 
                case 1:
                    Personaje objetivoAtk = elegirObjetivo(equipoRival);
                    String armaTxt = atacante.getArma() != null ? atacante.getArma().getNombreArma() : "puños desnudos";
                    int danio = atacante.getArma() != null ? atacante.getArma().getDanioBase() : 15;

                    System.out.println(AMARILLO + atacante.getNombrePersonaje() + " ataca con " + armaTxt + " a "
                            + objetivoAtk.getNombrePersonaje() + "!" + RESET);

                    int azarCritico = new java.util.Random().nextInt(100);
                    if (azarCritico < 20) {
                        System.out.println(AMARILLO + "¡GOLPE CRÍTICO! ¡El daño se duplica!" + RESET);
                        danio = danio * 2;
                    }

                    objetivoAtk.recibirDanio(danio);
                    turnoCompletado = true;
                    break;
                case 2:
                    if (atacante.getPerks().isEmpty()) {
                        System.out.println(ROJO + "Este personaje no tiene Perks asignadas." + RESET);
                    } else {
                        System.out.println(MORADO + "Elige qué Perk usar:" + RESET);
                        for (int i = 0; i < atacante.getPerks().size(); i++) {
                            Perk p = atacante.getPerks().get(i);
                            System.out.println(MORADO + "[" + (i + 1) + "] " + p.getNombre() + " (Usos: " + p.getUsos() + ")" + RESET);
                        }
                        System.out.println(MORADO + "[" + (atacante.getPerks().size() + 1) + "] Cancelar" + RESET);
                        int opPerk = errorNumero(sc);

                        if (opPerk >= 1 && opPerk <= atacante.getPerks().size()) {
                            Perk perkElegida = atacante.getPerks().get(opPerk - 1);
                            if (perkElegida.getUsos() > 0) {
                                Personaje objetivoPerk = elegirObjetivo(equipoRival);
                                perkElegida.lanzar(atacante, objetivoPerk);
                                perkElegida.consumirUso();
                                com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).desbloquearLogro(7); // Estratega
                                turnoCompletado = true;
                            } else {
                                System.out.println(ROJO + "No quedan usos de esta Perk." + RESET);
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.println(VERDE + atacante.getNombrePersonaje() + " adopta postura defensiva..." + RESET);
                    atacante.setDefendiendo(true);
                    turnoCompletado = true;
                    break;
                default:
                    System.out.println(ROJO + "Opción inválida." + RESET);
            }
        }
        return false;
    }

    /**
     * Muestra el menú de pausa in-game simulando el ESC de un juego real.
     * Retorna TRUE si el jugador quiere salir al menú principal.
     */
    private boolean abrirMenuPausa(int ronda) {
        System.out.println(BLANCO + "\n==== MENÚ DE PAUSA ====" + RESET);
        System.out.println("1. Reanudar partida");
        System.out.println("2. Guardar Partida (Elegir Ranura)");
        System.out.println("3. Abandonar Partida (Volver al Menú)");
        System.out.print(">>> Opción: ");
        int op = errorNumero(sc);
        
        if (op == 2) {
            com.dbd.dao.GestorPersistencia gestor = com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class);
            gestor.listarPartidas();
            System.out.print(AMARILLO + "Selecciona una ranura para sobrescribir (1-3) o 0 para cancelar: " + RESET);
            int slot = errorNumero(sc);
            if (slot >= 1 && slot <= 3) {
                this.idRanuraActual = slot;
                boolean exito = gestor.guardarPartida(slot, ronda, modoActual, false, supervivientes, killers);
                if (exito) {
                    System.out.println(VERDE + "Partida guardada exitosamente en la Ranura " + slot + RESET);
                }
            } else {
                System.out.println(ROJO + "Guardado cancelado." + RESET);
            }
            return false;
        } else if (op == 3) {
            System.out.println(ROJO + "Has abandonado la partida." + RESET);
            return true;
        }
        
        return false;
    }

    private Personaje elegirObjetivo(ArrayList<Personaje> equipoRival) {
        ArrayList<Personaje> vivos = new ArrayList<>();
        for (int i = 0; i < equipoRival.size(); i++) {
            if (equipoRival.get(i).getVidaActual() > 0) {
                vivos.add(equipoRival.get(i));
            }
        }

        while (true) {
            System.out.println(CYAN + "Selecciona un objetivo:" + RESET);
            for (int i = 0; i < vivos.size(); i++) {
                System.out.println(CYAN + "[" + (i + 1) + "] " + vivos.get(i).getNombrePersonaje() + " ("
                        + vivos.get(i).getVidaActual() + " HP)" + RESET);
            }
            int op = errorNumero(sc);
            if (op >= 1 && op <= vivos.size()) {
                return vivos.get(op - 1);
            } else {
                System.out.println(ROJO + "Opción no válida." + RESET);
            }
        }
    }

    public void iniciarManual() {
        System.out.println(CYAN + "\n--- SELECCIÓN DE SUPERVIVIENTES (Elige 3) ---" + RESET);
        while (supervivientes.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - supervivientes.size()));
            System.out.println(CYAN + "[1] Leon Kennedy  [2] Steve Harrington  [3] Feng Min  [4] Sable Ward\n[5] Mikaela Reid  [6] Ada Wong  [7] Lara Croft  [8] Nancy Wheeler" + RESET);
            System.out.print(">>> Elige: ");
            int op = errorNumero(sc);
            switch (op) {
                case 1: supervivientes.add(new LeonKennedy()); break;
                case 2: supervivientes.add(new SteveHarrington()); break;
                case 3: supervivientes.add(new FengMin()); break;
                case 4: supervivientes.add(new SableWard()); break;
                case 5: supervivientes.add(new Mikaela()); break;
                case 6: supervivientes.add(new AdaWong()); break;
                case 7: supervivientes.add(new LaraCroft()); break;
                case 8: supervivientes.add(new Nancy()); break;
                default: System.out.println(ROJO + "Opción no válida." + RESET); break;
            }
        }

        System.out.println(ROJO + "\n--- SELECCIÓN DE KILLERS (Elige 3) ---" + RESET);
        while (killers.size() < 3) {
            System.out.println("Espacios restantes: " + (3 - killers.size()));
            System.out.println(ROJO + "[1] GhostFace  [2] Legion  [3] Onryo  [4] Animatrónico\n[5] Ghoul  [6] Chucky  [7] Wesker  [8] La Cerda" + RESET);
            System.out.print(">>> Elige: ");
            int op = errorNumero(sc);
            switch (op) {
                case 1: killers.add(new GhostFace()); break;
                case 2: killers.add(new Legion()); break;
                case 3: killers.add(new Onryo()); break;
                case 4: killers.add(new Animatronico()); break;
                case 5: killers.add(new Ghoul()); break;
                case 6: killers.add(new Chucky()); break;
                case 7: killers.add(new Wesker()); break;
                case 8: killers.add(new LaCerda()); break;
                default: System.out.println(ROJO + "Opción no válida." + RESET); break;
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
        survisDisp.add(new LeonKennedy()); survisDisp.add(new SteveHarrington());
        survisDisp.add(new FengMin()); survisDisp.add(new SableWard());
        survisDisp.add(new Mikaela()); survisDisp.add(new AdaWong());
        survisDisp.add(new LaraCroft()); survisDisp.add(new Nancy());

        ArrayList<Personaje> killersDisp = new ArrayList<>();
        killersDisp.add(new GhostFace()); killersDisp.add(new Legion());
        killersDisp.add(new Onryo()); killersDisp.add(new Animatronico());
        killersDisp.add(new Ghoul()); killersDisp.add(new Chucky());
        killersDisp.add(new Wesker()); killersDisp.add(new LaCerda());

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
        armasSolamenteSurvis.add(new Pistola()); armasSolamenteSurvis.add(new Conjuro());
        armasSolamenteSurvis.add(new Bengala()); armasSolamenteSurvis.add(new GranadaCegadora());
        armasSolamenteSurvis.add(new CajaDeHerramientas());

        ArrayList<Arma> armasSolamenteKillers = new ArrayList<>();
        armasSolamenteKillers.add(new Hacha()); armasSolamenteKillers.add(new Cuchillo());
        armasSolamenteKillers.add(new VinculoDeCondena()); armasSolamenteKillers.add(new CuchilloDeCarnicero());
        armasSolamenteKillers.add(new CuchillaOculta()); armasSolamenteKillers.add(new TentaculoUroboros());

        for (Personaje s : supervivientes) {
            Collections.shuffle(armasSolamenteSurvis);
            String nom = armasSolamenteSurvis.get(0).getNombreArma();
            if (nom.equals("Pistola")) s.setArma(new Pistola());
            else if (nom.equals("Bengala")) s.setArma(new Bengala());
            else if (nom.equals("Granada Cegadora")) s.setArma(new GranadaCegadora());
            else if (nom.equals("Caja de Herramientas")) s.setArma(new CajaDeHerramientas());
            else s.setArma(new Conjuro());
        }

        for (Personaje k : killers) {
            Collections.shuffle(armasSolamenteKillers);
            String nom = armasSolamenteKillers.get(0).getNombreArma();
            if (nom.equals("Hacha")) k.setArma(new Hacha());
            else if (nom.equals("Cuchillo")) k.setArma(new Cuchillo());
            else if (nom.equals("Cuchillo de Carnicero")) k.setArma(new CuchilloDeCarnicero());
            else if (nom.equals("Cuchilla Oculta")) k.setArma(new CuchillaOculta());
            else if (nom.equals("Tentáculo Uroboros")) k.setArma(new TentaculoUroboros());
            else k.setArma(new VinculoDeCondena());
        }
    }

    private void repartirPerks() {
        for (Personaje s : supervivientes) {
            ArrayList<Perk> poolSurvis = new ArrayList<>();
            poolSurvis.add(new Adrenalina()); poolSurvis.add(new Agilidad());
            poolSurvis.add(new DejaVu()); poolSurvis.add(new DemuestraLoQueVales());
            poolSurvis.add(new ExtraOficial()); poolSurvis.add(new Fajador());
            poolSurvis.add(new GolpeDecisivo()); poolSurvis.add(new Inquebrantable());
            poolSurvis.add(new Liberacion()); poolSurvis.add(new LoLograremos());
            poolSurvis.add(new Oportunidades()); poolSurvis.add(new Parentesco());
            poolSurvis.add(new PersonajeSecundario()); poolSurvis.add(new Resiliencia());
            poolSurvis.add(new Sprint()); poolSurvis.add(new Clarividencia());
            poolSurvis.add(new CirculoDeCuracion()); poolSurvis.add(new AgenteEncubierto());
            poolSurvis.add(new Autocuracion()); poolSurvis.add(new PasoLigero());
            poolSurvis.add(new SentidoArcano()); poolSurvis.add(new Finesse());
            poolSurvis.add(new Especialista());

            Collections.shuffle(poolSurvis);
            for (int i = 0; i < 4; i++) s.addPerk(poolSurvis.get(i));
        }

        ArrayList<Perk> poolKillers = new ArrayList<>();
        poolKillers.add(new AbrazoLugubre()); poolKillers.add(new AmigosHastaElFin());
        poolKillers.add(new BarbacoaYChile()); poolKillers.add(new Desconcierto());
        poolKillers.add(new EnNingunLugarParaEsconderse()); poolKillers.add(new FuriaDelEspiritu());
        poolKillers.add(new IntervencionCorrupta()); poolKillers.add(new MaleficioJuguete());
        poolKillers.add(new NadieEscapaDeLaMuerte()); poolKillers.add(new PerseguidorLetal());
        poolKillers.add(new PimPamPum()); poolKillers.add(new PuntoMuerto());
        poolKillers.add(new Resistencia()); poolKillers.add(new ResonanciaDelDolor());
        poolKillers.add(new Sacudida()); poolKillers.add(new Erupcion());
        poolKillers.add(new TomarDecision()); poolKillers.add(new DespertarAncestral());
        poolKillers.add(new Despiadado()); poolKillers.add(new TrucoDelVerdugo());
        poolKillers.add(new AnatomiaSuperior()); poolKillers.add(new CarniceroChapucero());
        poolKillers.add(new MaleficioRuina());

        for (Personaje k : killers) {
            Collections.shuffle(poolKillers);
            for (int i = 0; i < 4; i++) k.addPerk(poolKillers.get(i));
        }
    }

    public void iniciarJuegoAutomatizado(int rondaInicio) {
        int ronda = rondaInicio;
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
            
            if (idRanuraActual != -1) {
                boolean guardado = com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class).guardarPartida(idRanuraActual, ronda, modoActual, false, supervivientes, killers);
                if(guardado) {
                    System.out.println(MORADO + "--> [BD] Progreso de ronda guardado automáticamente (Ranura " + idRanuraActual + ")." + RESET);
                }
            }
            
            ronda++;

            if (ronda > 50) {
                System.out.println(AMARILLO + "La batalla dura demasiado... ¡Empate técnico!" + RESET);
                break;
            }
        }
        anunciarGanador(ronda);
    }

    private boolean equipoVivo(ArrayList<Personaje> equipo) {
        for (Personaje p : equipo) {
            if (p.getVidaActual() > 0) return true;
        }
        return false;
    }

    private String dibujarBarraVida(int actual, int max) {
        if (actual < 0) actual = 0;
        int maxBarras = 10;
        int barrasLlenas = (int) Math.round((double) actual / max * maxBarras);
        String colorBarra;
        double porcentaje = (double) actual / max;
        if (porcentaje >= 0.6) colorBarra = VERDE;
        else if (porcentaje >= 0.3) colorBarra = AMARILLO;
        else colorBarra = ROJO;

        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < maxBarras; i++) {
            if (i < barrasLlenas) barra.append(colorBarra).append("█").append(RESET);
            else barra.append("░");
        }
        barra.append("]");
        return barra.toString();
    }

    private void mostrarEstadoEquipos() {
        System.out.println(AMARILLO + "\n--- ESTADO DE LA PARTIDA ---" + RESET);
        System.out.print(CYAN + "SUPERVIVIENTES:\n" + RESET);
        for (Personaje p : supervivientes) {
            String armaTxt = p.getArma() != null ? " [" + p.getArma().getNombreArma() + "]" : "";
            String status = p.getVidaActual() > 0 
                ? VERDE + p.getVidaActual() + "/" + p.getVidaMax() + " HP " + dibujarBarraVida(p.getVidaActual(), p.getVidaMax()) + armaTxt + RESET
                : ROJO + " MUERTO " + dibujarBarraVida(0, p.getVidaMax()) + RESET;
            System.out.println(CYAN + "> " + p.getNombrePersonaje() + ": " + status);
        }
        System.out.print(ROJO + "\nKILLERS (LA ENTIDAD):\n" + RESET);
        for (Personaje p : killers) {
            String armaTxt = p.getArma() != null ? " [" + p.getArma().getNombreArma() + "]" : "";
            String status = p.getVidaActual() > 0 
                ? VERDE + p.getVidaActual() + "/" + p.getVidaMax() + " HP " + dibujarBarraVida(p.getVidaActual(), p.getVidaMax()) + armaTxt + RESET
                : ROJO + " MUERTO " + dibujarBarraVida(0, p.getVidaMax()) + RESET;
            System.out.println(ROJO + "> " + p.getNombrePersonaje() + ": " + status);
        }
        System.out.println(AMARILLO + "----------------------------" + RESET);
    }

    private void anunciarGanador(int rondasTotales) {
        System.out.println(AMARILLO + "\n=========================================" + RESET);
        com.dbd.dao.GestorPersistencia gestor = com.dbd.core.SpringContextHolder.getBean(com.dbd.dao.GestorPersistencia.class);

        String bandoGanador = "";
        if (equipoVivo(supervivientes)) {
            System.out.println(CYAN + "  ¡LOS SUPERVIVIENTES HAN ESCAPADO!" + RESET);
            gestor.registrarVictoriaGlobal("Supervivientes");
            bandoGanador = "superviviente";
            gestor.desbloquearLogro(4); // Superviviente Nato
            
            // Verificar Trabajo en Equipo (todos vivos)
            boolean todosVivos = true;
            for (Personaje p : supervivientes) {
                if (p.getVidaActual() <= 0) todosVivos = false;
            }
            if (todosVivos) gestor.desbloquearLogro(5);
            
            // Sobrevivir al Límite (alguno con 1 de vida)
            for (Personaje p : supervivientes) {
                if (p.getVidaActual() == 1) gestor.desbloquearLogro(15);
                if (p.getVidaActual() == p.getVidaMax()) gestor.desbloquearLogro(2); // Intocable
            }
        } else {
            System.out.println(ROJO + "  SACRIFICIO COMPLETADO. LA ENTIDAD GANA." + RESET);
            gestor.registrarVictoriaGlobal("Killers");
            bandoGanador = "killer";
            gestor.desbloquearLogro(3); // Carnicero
            
            for (Personaje k : killers) {
                if (k.getVidaActual() == k.getVidaMax()) gestor.desbloquearLogro(2); // Intocable
            }
        }
        System.out.println(AMARILLO + "=========================================" + RESET);

        // Otros Logros Generales
        if (modoActual.equals("automatico")) gestor.desbloquearLogro(11); // Jugador Automático
        if (modoActual.equals("manual")) gestor.desbloquearLogro(12); // Jugador Manual
        if (rondasTotales >= 10) gestor.desbloquearLogro(18); // Resistencia Férrea

        // Guardar en Histórico
        gestor.guardarEnHistorico(modoActual, rondasTotales, bandoGanador, supervivientes, killers);

        if (idRanuraActual != -1) {
            // Eliminar la ranura porque la partida terminó
            gestor.borrarPartida(idRanuraActual);
        }
    }

    public void mostrarSupervivientes() {
        System.out.println(CYAN + "\n SUPERVIVIENTES DISPONIBLES:" + RESET);
        Personaje[] listaSurvis = { new LeonKennedy(), new SteveHarrington(), new FengMin(), new SableWard(), new Mikaela(), new AdaWong(), new LaraCroft(), new Nancy() };
        for (int i = 0; i < listaSurvis.length; i++) {
            System.out.println(" " + (i + 1) + ". " + listaSurvis[i].getNombrePersonaje() + " | Vida Base: " + VERDE + listaSurvis[i].getVidaActual() + " HP" + RESET);
        }
        System.out.println(CYAN + "-------------------------------------------------" + RESET);
    }

    public void mostrarAsesinos() {
        System.out.println(ROJO + "\n KILLERS DISPONIBLES (LA ENTIDAD):" + RESET);
        Personaje[] listaKillers = { new GhostFace(), new Legion(), new Onryo(), new Animatronico(), new Ghoul(), new Chucky(), new Wesker(), new LaCerda() };
        for (int i = 0; i < listaKillers.length; i++) {
            System.out.println(" " + (i + 1) + ". " + listaKillers[i].getNombrePersonaje() + " | Vida Base: " + VERDE + listaKillers[i].getVidaActual() + " HP" + RESET);
        }
        System.out.println(ROJO + "-------------------------------------------------" + RESET);
    }

    public void configurarPartida() {
        supervivientes.clear();
        killers.clear();
        idRanuraActual = -1;
    }
}
