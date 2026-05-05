package com.dbd.service;

import com.dbd.core.MotorTrial;
import com.dbd.dto.ActionRequest;
import com.dbd.dto.GameStateResponse;
import com.dbd.entidades.Personaje;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TrialService {

    // Instancia única temporal para la Fase 1. (En el futuro esto irá a
    // BBDD/Sesión).
    private MotorTrial motor;

    public GameStateResponse iniciarPartidaAleatoria() {
        motor = new MotorTrial();
        motor.configurarPartida();
        motor.iniciarAleatorio();
        return construirRespuesta(null);
    }

    public GameStateResponse iniciarPartidaPersonalizada(com.dbd.dto.CustomStartRequest request) {
        motor = new MotorTrial();
        motor.configurarPartida();

        ArrayList<Personaje> survis = new ArrayList<>();
        ArrayList<Personaje> killers = new ArrayList<>();

        if (request.getSurvivorIds() != null) {
            for (String id : request.getSurvivorIds()) {
                survis.add(mapSurvivor(id));
            }
        }

        if (request.getKillerIds() != null) {
            for (String id : request.getKillerIds()) {
                killers.add(mapKiller(id));
            }
        }

        motor.iniciarPersonalizado(survis, killers);
        return construirRespuesta(null);
    }

    private Personaje mapSurvivor(String id) {
        switch (id) {
            case "s1":
                return new com.dbd.entidades.AdaWong();
            case "s2":
                return new com.dbd.entidades.FengMin();
            case "s3":
                return new com.dbd.entidades.Mikaela();
            case "s4":
                return new com.dbd.entidades.Nancy();
            case "s5":
                return new com.dbd.entidades.LeonKennedy();
            case "s6":
                return new com.dbd.entidades.SableWard();
            case "s7":
                return new com.dbd.entidades.LaraCroft();
            case "s8":
                return new com.dbd.entidades.SteveHarrington();
            default:
                return new com.dbd.entidades.LeonKennedy(); // fallback
        }
    }

    private Personaje mapKiller(String id) {
        switch (id) {
            case "k1":
                return new com.dbd.entidades.GhostFace();
            case "k2":
                return new com.dbd.entidades.Legion();
            case "k3":
                return new com.dbd.entidades.Animatronico();
            case "k4":
                return new com.dbd.entidades.LaCerda();
            case "k5":
                return new com.dbd.entidades.Onryo();
            case "k6":
                return new com.dbd.entidades.Wesker();
            case "k7":
                return new com.dbd.entidades.Ghoul();
            case "k8":
                return new com.dbd.entidades.Chucky();
            default:
                return new com.dbd.entidades.GhostFace(); // fallback
        }
    }

    public GameStateResponse ejecutarAccion(ActionRequest request) {
        if (motor == null) {
            throw new IllegalStateException("La partida no ha comenzado.");
        }

        ArrayList<Personaje> atacantes;
        ArrayList<Personaje> rivales;

        // Por simplicidad, asumimos que "ATACAR" indica que es el turno actual
        // En una implementación real, tendríamos que saber si el atacante es Survi o
        // Killer
        // Vamos a deducirlo buscando el personaje en las listas
        Personaje actor = null;
        Personaje objetivo = null;

        // Buscar al actor
        if (request.getAtacanteIndex() < motor.getSupervivientes().size()) {
            actor = motor.getSupervivientes().get(request.getAtacanteIndex());
            atacantes = motor.getSupervivientes();
            rivales = motor.getKillers();
        } else {
            // Si no está en survis, buscamos en killers (adaptando el index)
            int kIndex = request.getAtacanteIndex() - motor.getSupervivientes().size();
            actor = motor.getKillers().get(kIndex);
            atacantes = motor.getKillers();
            rivales = motor.getSupervivientes();
        }

        // Buscar objetivo (si la acción lo requiere)
        if (request.getObjetivoIndex() < rivales.size()) {
            objetivo = rivales.get(request.getObjetivoIndex());
        }

        String logAction = "";

        switch (request.getTipoAccion().toUpperCase()) {
            case "ATACAR":
                if (actor != null && objetivo != null) {
                    int danio = actor.getArma() != null ? actor.getArma().getDanioBase() : 15;
                    objetivo.recibirDanio(danio);
                    logAction = actor.getNombrePersonaje() + " atacó a " + objetivo.getNombrePersonaje();
                }
                break;
            case "DEFENDER":
                if (actor != null) {
                    actor.setDefendiendo(true);
                    logAction = actor.getNombrePersonaje() + " adopta postura defensiva.";
                }
                break;
            case "PERK":
                if (actor != null && objetivo != null && request.getPerkIndex() < actor.getPerks().size()) {
                    var perk = actor.getPerks().get(request.getPerkIndex());
                    perk.lanzar(actor, objetivo);
                    perk.consumirUso();
                    logAction = actor.getNombrePersonaje() + " usó " + perk.getNombre() + " en "
                            + objetivo.getNombrePersonaje();
                }
                break;
        }

        // Verificar si la partida terminó
        boolean survisVivos = motor.getSupervivientes().stream().anyMatch(p -> p.getVidaActual() > 0);
        boolean killersVivos = motor.getKillers().stream().anyMatch(p -> p.getVidaActual() > 0);

        GameStateResponse response = construirRespuesta(logAction);

        if (!survisVivos || !killersVivos) {
            response.setPartidaTerminada(true);
            response.setGanador(survisVivos ? "Supervivientes" : "Killers");
            motor = null; // Reiniciar
        }

        return response;
    }

    private GameStateResponse construirRespuesta(String logAction) {
        GameStateResponse response = new GameStateResponse();
        if (motor != null) {
            response.setSupervivientes(motor.getSupervivientes());
            response.setKillers(motor.getKillers());
        }
        if (logAction != null && !logAction.isEmpty()) {
            response.addLog(logAction);
        }
        return response;
    }
}
