package com.dbd.service;

import com.dbd.core.MotorTrial;
import com.dbd.core.Logro;
import com.dbd.dao.GestorPersistencia;
import com.dbd.dto.ActionRequest;
import com.dbd.dto.GameStateResponse;
import com.dbd.dto.CharacterSelectionRequest;
import com.dbd.entidades.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class TrialService {
    
    @Autowired
    private GestorPersistencia gestorPersistencia;

    private MotorTrial motor;

    public Map<String, List<String>> getAvailableCharacters() {
        Map<String, List<String>> chars = new HashMap<>();
        chars.put("supervivientes", List.of("LeonKennedy", "SteveHarrington", "FengMin", "SableWard", "Mikaela", "AdaWong", "LaraCroft", "Nancy"));
        chars.put("killers", List.of("GhostFace", "Legion", "Onryo", "Animatronico", "Ghoul", "Chucky", "Wesker", "LaCerda"));
        return chars;
    }

    public List<Logro> getLogros() {
        return gestorPersistencia.cargarLogros();
    }

    public List<Map<String, Object>> getSaves() {
        return gestorPersistencia.obtenerPartidas();
    }

    public GameStateResponse cargarPartida(int idRanura) {
        ArrayList<Personaje> survis = new ArrayList<>();
        ArrayList<Personaje> killers = new ArrayList<>();
        Object[] meta = gestorPersistencia.cargarPartidaCompleta(idRanura, survis, killers);
        
        if (meta == null) {
            throw new IllegalStateException("Ranura vacía o partida no encontrada");
        }
        
        motor = new MotorTrial();
        motor.configurarPartida();
        motor.setIdRanuraActual(idRanura, (String) meta[1]);
        motor.iniciarWeb(survis, killers);
        
        return construirRespuesta("Partida reanudada desde la ranura " + idRanura);
    }

    public GameStateResponse getState() {
        if (motor == null) {
            throw new IllegalStateException("No hay partida en curso");
        }
        return construirRespuesta("Estado actual recuperado");
    }

    public GameStateResponse guardarPartidaWeb(int ranura) {
        if (motor == null) {
            throw new IllegalStateException("No hay partida activa para guardar");
        }
        boolean exito = gestorPersistencia.guardarPartida(ranura, 1, "manual", false, motor.getSupervivientes(), motor.getKillers());
        if (!exito) {
            throw new IllegalStateException("Fallo al guardar en BD");
        }
        motor.setIdRanuraActual(ranura, "manual");
        return construirRespuesta("Partida guardada exitosamente en la ranura " + ranura);
    }

    public GameStateResponse iniciarPartidaAleatoria() {
        motor = new MotorTrial();
        motor.configurarPartida();
        motor.iniciarAleatorio();
        return construirRespuesta(null);
    }

    public GameStateResponse iniciarPartidaManual(CharacterSelectionRequest request) {
        motor = new MotorTrial();
        motor.configurarPartida();
        
        ArrayList<Personaje> survis = new ArrayList<>();
        ArrayList<Personaje> killers = new ArrayList<>();

        for(String s : request.getSupervivientes()) {
            survis.add(instanciarPersonaje(s));
        }
        for(String k : request.getKillers()) {
            killers.add(instanciarPersonaje(k));
        }

        motor.iniciarWeb(survis, killers);
        return construirRespuesta(null);
    }

    private Personaje instanciarPersonaje(String nombreClase) {
        try {
            Class<?> clazz = Class.forName("com.dbd.entidades." + nombreClase);
            return (Personaje) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println("Error instanciando: " + nombreClase);
            try {
                return (Personaje) Class.forName("com.dbd.entidades.LeonKennedy").getDeclaredConstructor().newInstance();
            } catch (Exception ex) { return null; }
        }
    }

    public GameStateResponse ejecutarAccion(ActionRequest request) {
        if (motor == null) {
            throw new IllegalStateException("La partida no ha comenzado.");
        }

        ArrayList<Personaje> atacantes;
        ArrayList<Personaje> rivales;

        Personaje actor = null;
        Personaje objetivo = null;

        if (request.getAtacanteIndex() < motor.getSupervivientes().size()) {
            actor = motor.getSupervivientes().get(request.getAtacanteIndex());
            atacantes = motor.getSupervivientes();
            rivales = motor.getKillers();
        } else {
            int kIndex = request.getAtacanteIndex() - motor.getSupervivientes().size();
            actor = motor.getKillers().get(kIndex);
            atacantes = motor.getKillers();
            rivales = motor.getSupervivientes();
        }

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
                    logAction = actor.getNombrePersonaje() + " usó " + perk.getNombre() + " en " + objetivo.getNombrePersonaje();
                }
                break;
            default:
                logAction = "Acción ignorada por el servidor.";
                break;
        }

        boolean survisVivos = motor.getSupervivientes().stream().anyMatch(p -> p.getVidaActual() > 0);
        boolean killersVivos = motor.getKillers().stream().anyMatch(p -> p.getVidaActual() > 0);

        GameStateResponse response = construirRespuesta(logAction);

        if (!survisVivos || !killersVivos) {
            response.setPartidaTerminada(true);
            response.setGanador(survisVivos ? "Supervivientes" : "Killers");
            motor = null; 
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
